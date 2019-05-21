package com.xws.xysz.controller.merchant;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 额度申请相关控制器
 * Created by yangg on 2018/12/8.
 */
@Controller("merchantApply")
@RequestMapping("/merchant/apply")
public class ApplyController {
//    @Autowired
//    HttpServletRequest request;
//
//    @Autowired
//    ApplyRecordService applyRecordService;
//
//    @Autowired
//    VerifyService verifyService;
//
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    MarkContactsService markContactsService;
//
//
//    /**
//     * 查询所有额度申请列表
//     *
//     * @param pageNo      页码
//     * @param pageSize    每页显示数量
//     * @param orderId     贷款申请订单号
//     * @param projectName 产品名称（支持模糊搜索），可为空
//     * @param projectId   所属产品id
//     * @param memberId    所属用户Id
//     * @param memberInfo  所属用户名称/手机号/ID（支持模糊搜索），可为空
//     * @param state       额度申请状态
//     * @param startTime   申请时间范围开始点
//     * @param endTime     申请时间范围结束点
//     */
//    @RequestMapping(value = "/{pageNo}-{pageSize}", method = RequestMethod.GET)
//    @Token(method = TokenMethod.create)
//    public String list(@PathVariable String pageNo, @PathVariable String pageSize, Integer projectId
//            , Integer memberId, String orderId, String projectName, String memberInfo, String state
//            , String startTime, String endTime, Model model) {
//        Merchant merchant = (Merchant) request.getSession().getAttribute("merchantInfo");
//        PageInfo pageInfo = applyRecordService.searchList(pageNo, pageSize, state, merchant.getId(), memberId, projectId
//                , orderId, projectName, memberInfo, null, startTime, endTime);
//        model.addAttribute("pageInfo", pageInfo);
//        return "merchant/apply/list";
//    }
//
//    /**
//     * 修改审批额度
//     *
//     * @param id     申请额度的id
//     * @param amount 修改后的额度
//     * @return 之前的列表页
//     */
//    @RequestMapping(value = "/amount", method = RequestMethod.POST)
//    @Token(method = TokenMethod.verify)
//    public String amount(int id, BigDecimal amount, RedirectAttributes attributes) {
//        Merchant merchant = (Merchant) request.getSession().getAttribute("merchantInfo");
//        String refUrl = request.getHeader("Referer");
//        String msg = null;
//        try {
//            if (verifyService.applyAndMerchant(id, merchant.getId()) == false) {
//                msg = "此额度申请不属于该商户";
//            } else {
//                applyRecordService.updateAmount(id, amount);
//                msg = "修改审批额度成功";
//            }
//        } catch (WarnException e) {
//            msg = e.getMessage();
//        } finally {
//            return DispatcherUtil.AlertAndRedirect(msg, refUrl, attributes);
//        }
//    }
//
//    /**
//     * 进入额度审核页面
//     *
//     * @param id 额度申请id
//     */
//    @RequestMapping(value = "/audit", method = RequestMethod.GET)
//    public String audit(int id, String memberId, Model model, RedirectAttributes attributes) {
//        Merchant merchant = (Merchant) request.getSession().getAttribute("merchantInfo");
//        if (verifyService.applyAndMerchant(id, merchant.getId()) == false) {
//            return DispatcherUtil.AlertAndRedirect("此额度申请不属于该商户", "/merchant/apply/1-10", attributes);
//        }
//        try {
//            ApplyRecord applyRecord = applyRecordService.selectByRecordId(memberId, String.valueOf(id));
//            Member member = memberService.searchMemberById(applyRecord.getMemberId());
//            String haveContacts = memberService.haveByTel(member.getId(),member.getMobile());
//            model.addAttribute("member", member);
//            model.addAttribute("apply", applyRecord);
//            model.addAttribute("haveContacts",haveContacts);
//        } catch (WarnException e) {
//            return DispatcherUtil.AlertAndRedirect(e.getMessage(), "/merchant/apply/1-10", attributes);
//        }
//        return "merchant/apply/audit";
//    }
//
//
//    /**
//     * 额度审核
//     *
//     */
//    @RequestMapping(value = "/audit", method = RequestMethod.POST)
//    @ResponseBody
//    public String audit(ApplyRecord applyRecord, String type, RedirectAttributes attributes) {
//        Merchant merchant = (Merchant) request.getSession().getAttribute("merchantInfo");
//        String msg = null;
//        try {
//            if (verifyService.applyAndMerchant(applyRecord.getId(), merchant.getId()) == false) {
//                msg = "此额度申请不属于该商户";
//            } else {
//                applyRecordService.audit(applyRecord, type);
//            }
//        } catch (WarnException e) {
//            msg = e.getMessage();
//        } finally {
//            return msg;
//        }
//    }
//
//    /**
//     * 标记联系人信息
//     * @param relationId
//     * @param isCall
//     * @param relation
//     * @return
//     */
//    @RequestMapping(value = "/contacts/mark", method = RequestMethod.POST,produces = "application/json; charset=utf-8" )
//    @ResponseBody
//    public String contactsMark(int relationId,String isCall,String relation) {
//        Merchant merchant = (Merchant) request.getSession().getAttribute("merchantInfo");
//        String msg = null;
//        try {
//            markContactsService.contactsMark(merchant.getId(),relationId,isCall,relation);
//        } catch (WarnException e) {
//            msg = e.getMessage();
//        } finally {
//            return msg;
//        }
//    }

}
