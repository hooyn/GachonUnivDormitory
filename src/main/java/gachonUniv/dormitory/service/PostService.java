package gachonUniv.dormitory.service;

import gachonUniv.dormitory.domain.Post;
import gachonUniv.dormitory.dto.FindPostDto;
import gachonUniv.dormitory.dto.PostSearchCondition;
import gachonUniv.dormitory.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    /**
     * 게시글 작성
     */
    @Transactional
    public Long createPost(Post post){
        return postRepository.save(post);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long updatePost(Long id, String title, String content, String category, String[] hash){
        postRepository.update(id, title, content, category, hash);
        return id;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public Long deletePost(Long id){
        postRepository.delete(id);
        return id;
    }

    /**
     * 게시글 확인
     */
    @Transactional
    public FindPostDto findPostId(Long id){
        Post post = postRepository.findOneReturnPost(id);
        post.setView_count(post.getView_count()+1);
        return postRepository.findOne(id);
    }

    /**
     * 게시글 전체 조회
     */
    @Transactional(readOnly = true)
    public List<FindPostDto> findPosts(Integer page){
        return postRepository.findAll(page);
    }

    /**
     * 게시글 전체 검색
     */
    @Transactional(readOnly = true)
    public List<FindPostDto> searchPosts(PostSearchCondition condition, Integer page){
        return postRepository.search(condition, page);
    }

    /**
     * 게시글 UUID에 따른 조회
     */
    @Transactional(readOnly = true)
    public List<FindPostDto> findPostUuid(String uuid, Integer page){
        return postRepository.findByUuid(uuid, page);
    }

    /**
     * 글에 대한 권한 확인
     */
    @Transactional(readOnly = true)
    public boolean checkPostAuth(String uuid, Long id){
        return postRepository.checkAuthorization(uuid, id);
    }
}
