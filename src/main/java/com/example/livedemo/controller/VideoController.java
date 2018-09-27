package com.example.livedemo.controller;

import com.example.livedemo.entity.VideoDemand;
import com.example.livedemo.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VideoController {
    @Autowired
    private DemandService demandService;

    /**
     * 获取列表
     * @param page
     * @param size
     * @param sort
     * @param direction
     * @return
     */
    @RequestMapping("")
    public String list(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "1000") int size,
                                   @RequestParam(value = "sort", defaultValue = "id") String sort,
                                   @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                   @RequestParam(value = "type", defaultValue = "0") String type,
                                   Model model){
        Sort sortDate = new Sort(Sort.Direction.ASC, sort);
        Pageable pageable = PageRequest.of(page, size, sortDate);
        Page<VideoDemand> videoDemandPage = demandService.getDemand(type, pageable);

        model.addAttribute("videoList", videoDemandPage.getContent());

        return "/list";
    }

    @GetMapping("upload")
    public String upload(){
        return "redirect:upload.html";
    }
}
