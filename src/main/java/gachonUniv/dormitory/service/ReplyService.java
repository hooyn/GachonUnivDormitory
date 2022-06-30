package gachonUniv.dormitory.service;

import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.domain.Post;
import gachonUniv.dormitory.domain.Reply;
import gachonUniv.dormitory.dto.FindReplyDto;
import gachonUniv.dormitory.repository.MemberRepository;
import gachonUniv.dormitory.repository.PostRepository;
import gachonUniv.dormitory.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 생성
     */
    @Transactional
    public Long createReply(String uuid, Long post_id, String content){
        Member member = memberRepository.findByUuid(uuid);
        Post post = postRepository.findOneReturnPost(post_id);
        post.setReply_count(post.getReply_count()+1);

        Reply reply = new Reply(content, member, post);
        Long id = replyRepository.save(reply);

        return id;
    }

    /**
     * 댓글 조회 (게시글 아이디에 따른)
     */
    @Transactional(readOnly = true)
    public List<FindReplyDto> findReplyPostId(Long post_id, Integer page){
        return replyRepository.findByPostId(post_id, page);
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public Long updateReply(Long reply_id, String content){
        return replyRepository.updateReply(reply_id, content);
    }

    /**
     * 댓글 권한 확인
     */
    @Transactional(readOnly = true)
    public boolean checkReplyAuth(String uuid, Long reply_id){
        return replyRepository.checkReplyAuthorization(uuid, reply_id);
    }
}
