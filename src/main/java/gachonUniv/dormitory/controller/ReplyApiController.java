package gachonUniv.dormitory.controller;

import gachonUniv.dormitory.dto.FindReplyDto;
import gachonUniv.dormitory.request.replyReq.FindReplyRequest;
import gachonUniv.dormitory.request.replyReq.WriteReplyRequest;
import gachonUniv.dormitory.response.Response;
import gachonUniv.dormitory.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {
    private final ReplyService replyService;

    @PostMapping("/reply")
    public Response writeReply(@RequestBody WriteReplyRequest request){
        Long id = replyService.createReply(request.getUuid(), request.getPost_id(), request.getContent());

        return new Response(true, HttpStatus.OK.value(), id, "댓글이 작성되었습니다.");
    }

    @PostMapping("/replyies")
    public Response findReplies(@RequestBody FindReplyRequest request){
        List<FindReplyDto> data = replyService.findReplyPostId(request.getPost_id());

        return new Response(true, HttpStatus.OK.value(), data, request.getPost_id()+" 게시글의 댓글이 조회되었습니다.");
    }
}
