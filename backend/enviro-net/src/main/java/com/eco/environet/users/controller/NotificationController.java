package com.eco.environet.users.controller;

import com.eco.environet.users.dto.NotificationDto;
import com.eco.environet.users.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "User Notifications", description = "Manage user notifications")
public class NotificationController {
    private final NotificationService service;

    @Operation(summary = "Get all user notifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all user notifications",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDto[].class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping(value="/all/{id}")
    @PreAuthorize("isAuthenticated() and #id == authentication.principal.id")
    public ResponseEntity<List<NotificationDto>> findAllByUser(@PathVariable Long id) {
        var result = service.findAllByUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Get all unread user notifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all unread user notifications",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDto[].class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping(value="/unread/{id}")
    @PreAuthorize("isAuthenticated() and #id == authentication.principal.id")
    public ResponseEntity<List<NotificationDto>> findAllUnreadByUser(@PathVariable Long id) {
        var result = service.findAllUnreadByUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Send notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New notification sent!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(value = "/new", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NotificationDto> sendNotification(@RequestBody NotificationDto newNotificationDto) {
        var result = service.sendNotification(newNotificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Send notification and email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New notification and email sent!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(value = "/new/email", consumes = "application/json")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NotificationDto> sendNotificationAndEmail(@RequestBody NotificationDto newNotificationDto) {
        var result = service.sendNotificationAndEmail(newNotificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Read notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marked as read.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping(value = "/read", consumes = "application/json")
    @PreAuthorize("isAuthenticated() and @notificationRepository.findById(#notificationDto.id)?.orElse(null)?.getUser()?.getId() == authentication.principal.id")
    public ResponseEntity<NotificationDto> readNotification(@RequestBody NotificationDto notificationDto) {
        var result = service.readNotification(notificationDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Mark notification as unread")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marked as unread.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping(value = "/unread", consumes = "application/json")
    @PreAuthorize("isAuthenticated() and @notificationRepository.findById(#notificationDto.id)?.orElse(null)?.getUser()?.getId() == authentication.principal.id")
    public ResponseEntity<NotificationDto> markAsUnread(@RequestBody NotificationDto notificationDto) {
        var result = service.markAsUnread(notificationDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Read all user notifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Read all user notifications",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDto[].class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping(value="/read/all/{id}")
    @PreAuthorize("isAuthenticated() and #id == authentication.principal.id")
    public ResponseEntity<List<NotificationDto>> readAllByUser(@PathVariable Long id) {
        var result = service.readAllByUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Delete notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted notification!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("isAuthenticated() and @notificationRepository.findById(#id)?.orElse(null)?.getUser()?.getId() == authentication.principal.id")
    public ResponseEntity<NotificationDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
