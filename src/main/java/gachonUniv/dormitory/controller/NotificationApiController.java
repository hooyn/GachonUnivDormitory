package gachonUniv.dormitory.controller;

import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.domain.Notification;
import gachonUniv.dormitory.domain.Post;
import gachonUniv.dormitory.dto.FindNotificationDto;
import gachonUniv.dormitory.repository.PostRepository;
import gachonUniv.dormitory.request.notificationReq.CreateNotificationRequest;
import gachonUniv.dormitory.request.notificationReq.FindNotificationRequest;
import gachonUniv.dormitory.response.Response;
import gachonUniv.dormitory.service.MemberService;
import gachonUniv.dormitory.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationService notificationService;
    private final MemberService memberService;
    private final PostRepository postRepository;

    @PostMapping("/notification")
    public Response createNotification(@RequestBody CreateNotificationRequest request){
        Member member = memberService.findMemberUuid(request.getUuid());
        Post post = postRepository.findOneReturnPost(request.getPost_id());

        Notification notification = new Notification(post, member, request.getTitle(), request.getContent());
        Long id = notificationService.createNotification(notification);

        return new Response(true, HttpStatus.OK.value(), id, "알림이 저장되었습니다.");
    }

    @GetMapping("/notifications")
    public Response findNotificationByUuid(@RequestBody FindNotificationRequest request, @RequestParam("page") Integer page){
        List<FindNotificationDto> data = notificationService.findNotificationByUuid(request.getUuid(), page);

        return new Response(true, HttpStatus.OK.value(), data, "알림이 조회되었습니다.");
    }
}
