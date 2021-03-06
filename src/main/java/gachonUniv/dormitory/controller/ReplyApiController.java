package gachonUniv.dormitory.controller;

import gachonUniv.dormitory.dto.FindReplyDto;
import gachonUniv.dormitory.request.replyReq.FindReplyRequest;
import gachonUniv.dormitory.request.replyReq.UpdateReplyRequest;
import gachonUniv.dormitory.request.replyReq.WriteReplyRequest;
import gachonUniv.dormitory.response.Response;
import gachonUniv.dormitory.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {
    private final ReplyService replyService;

    @GetMapping("/reply")
    public Response findReplies(@RequestBody FindReplyRequest request, @RequestParam("page") Integer page){
        List<FindReplyDto> data = replyService.findReplyPostId(request.getPost_id(), page);

        return new Response(true, HttpStatus.OK.value(), data, request.getPost_id()+" 게시글의 댓글이 조회되었습니다.");
    }

    @PostMapping("/reply")
    public Response writeReply(@RequestBody WriteReplyRequest request){
        Long id = replyService.createReply(request.getUuid(), request.getPost_id(), request.getContent());

        return new Response(true, HttpStatus.OK.value(), id, "댓글이 작성되었습니다.");
    }

    @PutMapping("/reply")
    public Response updateReply(@RequestBody UpdateReplyRequest request){
        boolean check = replyService.checkReplyAuth(request.getUuid(), request.getReply_id());

        if(check){
            Long id = replyService.updateReply(request.getReply_id(), request.getContent());

            return new Response(true, HttpStatus.OK.value(), id, "댓글이 수정되었습니다.");
        } else {
            return new Response(false, HttpStatus.BAD_REQUEST.value(), null, "댓글에 대한 권한이 없습니다.");
        }
    }
}
