package gachonUniv.dormitory.controller;

import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.domain.Post;
import gachonUniv.dormitory.dto.FindPostDto;
import gachonUniv.dormitory.request.postReq.DeletePostRequest;
import gachonUniv.dormitory.request.postReq.FindPostsRequest;
import gachonUniv.dormitory.request.postReq.WritePostRequest;
import gachonUniv.dormitory.response.Response;
import gachonUniv.dormitory.service.MemberService;
import gachonUniv.dormitory.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/post_write")
    public Response writePost(@RequestBody WritePostRequest request){
        Member member = memberService.findMemberUuid(request.getUuid());
        String[] hash = request.getHash();
        Post post = new Post(member, request.getTitle(), request.getContent(), request.getCategory(), hash[0], hash[1], hash[2]);

        Long id = postService.createPost(post);
        return new Response(true, HttpStatus.OK.value(), id, "게시글이 작성되었습니다.");
    }
    
    @GetMapping("/posts")
    public Response findPosts(){
        List<FindPostDto> data = postService.findPosts();
        return new Response(true, HttpStatus.OK.value(), data, "전체 게시글이 조회되었습니다.");
    }
    
    @PostMapping("/post_delete")
    public Response deletePost(@RequestBody DeletePostRequest request){
        boolean check = postService.checkPostAuth(request.getUuid(), request.getId());
        
        if(check){
            Long id = postService.deletePost(request.getId());
            return new Response(true, HttpStatus.OK.value(), id, "게시글이 삭제되었습니다.");
        } else {
            return new Response(false, HttpStatus.BAD_REQUEST.value(), null, "게시글에 대한 권한이 없습니다.");
        }

       
    }
}
