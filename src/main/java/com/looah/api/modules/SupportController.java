package com.looah.api.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.looah.api.modules.internal.SupportService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * 2011.01.06
 * @author ImYoon
 *
 */
@Controller
public class SupportController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SupportService supportService;
    
    @RequestMapping(value="/language-set/list",method=GET)
    public ModelAndView getLanguageSetList(){
        return new ModelAndView().addObject("data", supportService.getLanguageSetList());
    }
    
}
