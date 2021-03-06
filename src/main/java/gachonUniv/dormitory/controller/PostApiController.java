package gachonUniv.dormitory.controller;

import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.domain.Post;
import gachonUniv.dormitory.dto.FindPostDto;
import gachonUniv.dormitory.dto.PostSearchCondition;
import gachonUniv.dormitory.request.postReq.DeletePostRequest;
import gachonUniv.dormitory.request.postReq.FindPostsRequest;
import gachonUniv.dormitory.request.postReq.UpdatePostRequest;
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

    @GetMapping("/home/posts/all")
    public Response findPosts(@RequestParam("page") Integer page){
        List<FindPostDto> data = postService.findPosts(page);
        return new Response(true, HttpStatus.OK.value(), data, "전체 게시글이 조회되었습니다.");
    }

    //home에서 사용하는 최근 게시글 조회
    @GetMapping("/home/posts")
    public Response findPostsRecently(@RequestParam("page") Integer page){
        List<FindPostDto> data = postService.findPostRecently(page);
        return new Response(true, HttpStatus.OK.value(), data, "최근 게시글이 조회되었습니다.");
    }

    @GetMapping("/posts")
    public Response findPosts(@RequestBody PostSearchCondition condition, @RequestParam("page") Integer page){
        List<FindPostDto> data = postService.searchPosts(condition, page);
        return new Response(true, HttpStatus.OK.value(), data, "검색을 통한 게시글이 조회되었습니다.");
    }

    @PostMapping("/posts")
    public Response findPostsByUuid(@RequestBody FindPostsRequest request, @RequestParam("page") Integer page){
        List<FindPostDto> data = postService.findPostUuid(request.getUuid(), page);
        return new Response(true, HttpStatus.OK.value(), data, "사용자가 작성한 게시글이 조회되었습니다.");
    }

    @PostMapping("/post")
    public Response writePost(@RequestBody WritePostRequest request){
        Member member = memberService.findMemberUuid(request.getUuid());
        String[] hash = request.getHash();
        Post post = new Post(member, request.getTitle(), request.getContent(), request.getCategory(), hash[0], hash[1], hash[2]);

        Long id = postService.createPost(post);
        return new Response(true, HttpStatus.OK.value(), id, "게시글이 작성되었습니다.");
    }

    @GetMapping("/post")
    public Response findPost(@RequestBody FindPostsRequest request){
        boolean check = postService.checkPost(request.getId());

        if(!check){
            return new Response(false, HttpStatus.BAD_REQUEST.value(), null, "게시글이 존재하지 않습니다.");
        } else {
            FindPostDto data = postService.findPostId(request.getId());
            return new Response(true, HttpStatus.OK.value(), data, request.getId() + "게시글이 조회되었습니다.");
        }
    }

    @PutMapping("/post")
    public Response updatePost(@RequestBody UpdatePostRequest request){
        boolean check = postService.checkPostAuth(request.getUuid(), request.getId());

        if(check){
            Long id = postService.updatePost(request.getId(), request.getTitle(), request.getContent(), request.getCategory(), request.getHash());
            return new Response(true, HttpStatus.OK.value(), id, "게시글이 수정되었습니다.");
        } else {
            return new Response(false, HttpStatus.BAD_REQUEST.value(), null, "게시글에 대한 권한이 없습니다.");
        }
    }
    
    @DeleteMapping("/post")
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
