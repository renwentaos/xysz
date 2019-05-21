package com.xws.xysz.controller.admin;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 借款客户相关
 * Created by yangg on 2018/11/21.
 */
@Controller("adminMember")
@RequestMapping("/admin/member")
public class MemberController {

//    @Autowired
//    HttpServletRequest request;
//
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    VoucherRecordService voucherRecordService;
//
//
//    /**
//     * 查询所有用户信息列表
//     *
//     * @param pageNo     页码
//     * @param pageSize   每页显示数量
//     * @param merchantId 商户ID
//     * @param level      客户等级
//     * @param memberInfo 会员昵称/手机号/姓名（支持模糊搜索），可为空
//     * @param startTime  注册时间开始范围
//     * @param endTime    注册时间结束范围
//     * @param state      用户状态
//     */
//    @RequestMapping(value = "/{pageNo}-{pageSize}", method = RequestMethod.GET)
//    public String list(@PathVariable String pageNo, @PathVariable String pageSize, Integer merchantId, String memberInfo
//            , String level, String startTime, String endTime, String state, Integer channelId, Model model) {
//        PageInfo pageInfo = memberService.searchList(state, pageNo, pageSize,merchantId, memberInfo, null, level, startTime, endTime,channelId);
//        model.addAttribute("pageInfo", pageInfo);
//        return "admin/member/list";
//    }
//
//    /**
//     * 查看用户详细信息
//     *
//     * @param id
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "/detail", method = RequestMethod.GET)
//    public String search(String id, Model model) {
//        Member member = memberService.searchMemberById(Integer.valueOf(id));
//        Member invite = memberService.searchInviteMerchantById(Integer.valueOf(id));
//        List<VoucherRecord>  list = voucherRecordService.selectVoucherListWeb(id);
//        List<MemberContacts> memberContacts = memberService.getContactsTypeByMemberId(member.getId());
//        model.addAttribute("list",list);
//        model.addAttribute("memberContacts",memberContacts);
//        model.addAttribute("member", member);
//        model.addAttribute("invite",invite);
//        return "admin/member/detail";
//    }
//
//    /**
//     * 冻结  解冻客户
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/state", method = RequestMethod.GET)
//    @ResponseBody
//    public String updateState(String id) {
//        memberService.updateStateById(Integer.valueOf(id));
//        return null;
//    }
//
//    /**
//     * 更改代金券的状态
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/voucher/update/state", method = RequestMethod.POST,produces = "application/json; charset=utf-8" )
//    @ResponseBody
//    public String updateVoucherState(String id) {
//        voucherRecordService.updateVoucherStateById(id);
//        return null;
//    }
//
//
//    /**
//     * 会员用户添加备注
//     * @param memberId
//     * @param remark
//     * @return
//     */
//    @RequestMapping(value = "/remark",produces = "application/json; charset=utf-8",method = RequestMethod.GET)
//    @ResponseBody
//    public String updateRemark(String memberId,String remark) {
//        try {
//            memberService.addRemarkById(Integer.valueOf(memberId),remark);
//            return null;
//        }catch (WarnException e){
//            return e.getMessage();
//        }
//    }
}
