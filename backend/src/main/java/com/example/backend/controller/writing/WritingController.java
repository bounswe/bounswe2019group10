package com.example.backend.controller.writing;


import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.writing.WritingDTO;
import com.example.backend.service.member.JwtUserDetailsService;
import com.example.backend.service.writing.WritingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/writing")
@CrossOrigin
public class WritingController {

    @Autowired
    private WritingService writingService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @GetMapping("/{writingId}")
    @ApiOperation(value = "Get Writing by ID")
    public ResponseEntity<WritingDTO> getById(@PathVariable int writingId) {
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.getById(writingId, memberUsername));
    }

}
