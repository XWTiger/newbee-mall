/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ltd.newbee.mall.common.*;
import ltd.newbee.mall.controller.vo.*;
import ltd.newbee.mall.dao.*;
import ltd.newbee.mall.entity.*;
import ltd.newbee.mall.entity.common.ImgOrder;
import ltd.newbee.mall.entity.common.Notice;
import ltd.newbee.mall.entity.common.PageCL;
import ltd.newbee.mall.entity.lottery.football.*;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import ltd.newbee.mall.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class NewBeeMallOrderServiceImpl implements NewBeeMallOrderService {

    @Autowired
    private NewBeeMallOrderMapper newBeeMallOrderMapper;
    @Autowired
    private NewBeeMallOrderItemMapper newBeeMallOrderItemMapper;
    @Autowired
    private NewBeeMallShoppingCartItemMapper newBeeMallShoppingCartItemMapper;
    @Autowired
    private NewBeeMallGoodsMapper newBeeMallGoodsMapper;

    @Autowired
    private MallUserMapper mallUserMapper;

    @Autowired
    private OddsOrderMapper oddsOrderMapper;

    @Autowired
    private UserHappyNumMapper userHappyNumMapper;

    @Autowired
    private LotteryOrderMapper lotteryOrderMapper;

    @Autowired
    private CrsOrderMapper crsOrderMapper;

    @Autowired
    private HalfCourtOrderMapper halfCourtOrderMapper;

    @Autowired
    private TtgOrderMapper ttgOrderMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private ImgOrderMapper imgOrderMapper;


    @Override
    public PageResult getNewBeeMallOrdersPage(PageQueryUtil pageUtil) {
        List<NewBeeMallOrder> newBeeMallOrders = newBeeMallOrderMapper.findNewBeeMallOrderList(pageUtil);
        int total = newBeeMallOrderMapper.getTotalNewBeeMallOrders(pageUtil);
        PageResult pageResult = new PageResult(newBeeMallOrders, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String updateOrderInfo(NewBeeMallOrder newBeeMallOrder) {
        NewBeeMallOrder temp = newBeeMallOrderMapper.selectByPrimaryKey(newBeeMallOrder.getOrderId());
        //不为空且orderStatus>=0且状态为出库之前可以修改部分信息
        if (temp != null && temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3) {
            temp.setTotalPrice(newBeeMallOrder.getTotalPrice());
            temp.setUserAddress(newBeeMallOrder.getUserAddress());
            temp.setUpdateTime(new Date());
            if (newBeeMallOrderMapper.updateByPrimaryKeySelective(temp) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkDone(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<NewBeeMallOrder> orders = newBeeMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (NewBeeMallOrder newBeeMallOrder : orders) {
                if (newBeeMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += newBeeMallOrder.getOrderNo() + " ";
                    continue;
                }
                if (newBeeMallOrder.getOrderStatus() != 1) {
                    errorOrderNos += newBeeMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行配货完成操作 修改订单状态和更新时间
                if (newBeeMallOrderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功的订单，无法执行配货完成操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkOut(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<NewBeeMallOrder> orders = newBeeMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (NewBeeMallOrder newBeeMallOrder : orders) {
                if (newBeeMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += newBeeMallOrder.getOrderNo() + " ";
                    continue;
                }
                if (newBeeMallOrder.getOrderStatus() != 1 && newBeeMallOrder.getOrderStatus() != 2) {
                    errorOrderNos += newBeeMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行出库操作 修改订单状态和更新时间
                if (newBeeMallOrderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功或配货完成无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功或配货完成的订单，无法执行出库操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String closeOrder(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<NewBeeMallOrder> orders = newBeeMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (NewBeeMallOrder newBeeMallOrder : orders) {
                // isDeleted=1 一定为已关闭订单
                if (newBeeMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += newBeeMallOrder.getOrderNo() + " ";
                    continue;
                }
                //已关闭或者已完成无法关闭订单
                if (newBeeMallOrder.getOrderStatus() == 4 || newBeeMallOrder.getOrderStatus() < 0) {
                    errorOrderNos += newBeeMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行关闭操作 修改订单状态和更新时间
                if (newBeeMallOrderMapper.closeOrder(Arrays.asList(ids), NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行关闭操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单不能执行关闭操作";
                } else {
                    return "你选择的订单不能执行关闭操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String saveOrder(NewBeeMallUserVO user, List<NewBeeMallShoppingCartItemVO> myShoppingCartItems) {
        List<Long> itemIdList = myShoppingCartItems.stream().map(NewBeeMallShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> goodsIds = myShoppingCartItems.stream().map(NewBeeMallShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
        List<NewBeeMallGoods> newBeeMallGoods = newBeeMallGoodsMapper.selectByPrimaryKeys(goodsIds);
        //检查是否包含已下架商品
        List<NewBeeMallGoods> goodsListNotSelling = newBeeMallGoods.stream()
                .filter(newBeeMallGoodsTemp -> newBeeMallGoodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
            //goodsListNotSelling 对象非空则表示有下架商品
            NewBeeMallException.fail(goodsListNotSelling.get(0).getGoodsName() + "已下架，无法生成订单");
        }
        Map<Long, NewBeeMallGoods> newBeeMallGoodsMap = newBeeMallGoods.stream().collect(Collectors.toMap(NewBeeMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        //判断商品库存
        for (NewBeeMallShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
            //查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
            if (!newBeeMallGoodsMap.containsKey(shoppingCartItemVO.getGoodsId())) {
                NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            //存在数量大于库存的情况，直接返回错误提醒
            if (shoppingCartItemVO.getGoodsCount() > newBeeMallGoodsMap.get(shoppingCartItemVO.getGoodsId()).getStockNum()) {
                NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        //删除购物项
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(newBeeMallGoods)) {
            if (newBeeMallShoppingCartItemMapper.deleteBatch(itemIdList) > 0) {
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);
                int updateStockNumResult = newBeeMallGoodsMapper.updateStockNum(stockNumDTOS);
                if (updateStockNumResult < 1) {
                    NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //生成订单号
                String orderNo = NumberUtil.genOrderNo();
                int priceTotal = 0;
                //保存订单
                NewBeeMallOrder newBeeMallOrder = new NewBeeMallOrder();
                newBeeMallOrder.setOrderNo(orderNo);
                newBeeMallOrder.setUserId(user.getUserId());
                newBeeMallOrder.setUserAddress(user.getAddress());
                //总价
                for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
                    priceTotal += newBeeMallShoppingCartItemVO.getGoodsCount() * newBeeMallShoppingCartItemVO.getSellingPrice();
                }
                if (priceTotal < 1) {
                    NewBeeMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                newBeeMallOrder.setTotalPrice(priceTotal);
                //订单body字段，用来作为生成支付单描述信息，暂时未接入第三方支付接口，故该字段暂时设为空字符串
                String extraInfo = "";
                newBeeMallOrder.setExtraInfo(extraInfo);
                //生成订单项并保存订单项纪录
                if (newBeeMallOrderMapper.insertSelective(newBeeMallOrder) > 0) {
                    //生成所有的订单项快照，并保存至数据库
                    List<NewBeeMallOrderItem> newBeeMallOrderItems = new ArrayList<>();
                    for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
                        NewBeeMallOrderItem newBeeMallOrderItem = new NewBeeMallOrderItem();
                        //使用BeanUtil工具类将newBeeMallShoppingCartItemVO中的属性复制到newBeeMallOrderItem对象中
                        BeanUtil.copyProperties(newBeeMallShoppingCartItemVO, newBeeMallOrderItem);
                        //NewBeeMallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
                        newBeeMallOrderItem.setOrderId(newBeeMallOrder.getOrderId());
                        newBeeMallOrderItems.add(newBeeMallOrderItem);
                    }
                    //保存至数据库
                    if (newBeeMallOrderItemMapper.insertBatch(newBeeMallOrderItems) > 0) {
                        //所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
                        return orderNo;
                    }
                    NewBeeMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                NewBeeMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            NewBeeMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }

    @Override
    public NewBeeMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (newBeeMallOrder != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(newBeeMallOrder.getUserId())) {
                NewBeeMallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            }
            List<NewBeeMallOrderItem> orderItems = newBeeMallOrderItemMapper.selectByOrderId(newBeeMallOrder.getOrderId());
            //获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<NewBeeMallOrderItemVO> newBeeMallOrderItemVOS = BeanUtil.copyList(orderItems, NewBeeMallOrderItemVO.class);
                NewBeeMallOrderDetailVO newBeeMallOrderDetailVO = new NewBeeMallOrderDetailVO();
                BeanUtil.copyProperties(newBeeMallOrder, newBeeMallOrderDetailVO);
                newBeeMallOrderDetailVO.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(newBeeMallOrderDetailVO.getOrderStatus()).getName());
                newBeeMallOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(newBeeMallOrderDetailVO.getPayType()).getName());
                newBeeMallOrderDetailVO.setNewBeeMallOrderItemVOS(newBeeMallOrderItemVOS);
                return newBeeMallOrderDetailVO;
            }
        }
        return null;
    }

    @Override
    public NewBeeMallOrder getNewBeeMallOrderByOrderNo(String orderNo) {
        return newBeeMallOrderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = newBeeMallOrderMapper.getTotalNewBeeMallOrders(pageUtil);
        List<NewBeeMallOrder> newBeeMallOrders = newBeeMallOrderMapper.findNewBeeMallOrderList(pageUtil);
        List<NewBeeMallOrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //数据转换 将实体类转成vo
            orderListVOS = BeanUtil.copyList(newBeeMallOrders, NewBeeMallOrderListVO.class);
            //设置订单状态中文显示值
            for (NewBeeMallOrderListVO newBeeMallOrderListVO : orderListVOS) {
                newBeeMallOrderListVO.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(newBeeMallOrderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = newBeeMallOrders.stream().map(NewBeeMallOrder::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<NewBeeMallOrderItem> orderItems = newBeeMallOrderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<NewBeeMallOrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(NewBeeMallOrderItem::getOrderId));
                for (NewBeeMallOrderListVO newBeeMallOrderListVO : orderListVOS) {
                    //封装每个订单列表对象的订单项数据
                    if (itemByOrderIdMap.containsKey(newBeeMallOrderListVO.getOrderId())) {
                        List<NewBeeMallOrderItem> orderItemListTemp = itemByOrderIdMap.get(newBeeMallOrderListVO.getOrderId());
                        //将NewBeeMallOrderItem对象列表转换成NewBeeMallOrderItemVO对象列表
                        List<NewBeeMallOrderItemVO> newBeeMallOrderItemVOS = BeanUtil.copyList(orderItemListTemp, NewBeeMallOrderItemVO.class);
                        newBeeMallOrderListVO.setNewBeeMallOrderItemVOS(newBeeMallOrderItemVOS);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String cancelOrder(String orderNo, Long userId) {
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (newBeeMallOrder != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(newBeeMallOrder.getUserId())) {
                NewBeeMallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            }
            //订单状态判断
            if (newBeeMallOrder.getOrderStatus().intValue() == NewBeeMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus()
                    || newBeeMallOrder.getOrderStatus().intValue() == NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()
                    || newBeeMallOrder.getOrderStatus().intValue() == NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
                    || newBeeMallOrder.getOrderStatus().intValue() == NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            if (newBeeMallOrderMapper.closeOrder(Collections.singletonList(newBeeMallOrder.getOrderId()), NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String finishOrder(String orderNo, Long userId) {
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (newBeeMallOrder != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(newBeeMallOrder.getUserId())) {
                return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
            }
            //订单状态判断 非出库状态下不进行修改操作
            if (newBeeMallOrder.getOrderStatus().intValue() != NewBeeMallOrderStatusEnum.ORDER_EXPRESS.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            newBeeMallOrder.setOrderStatus((byte) NewBeeMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            newBeeMallOrder.setUpdateTime(new Date());
            if (newBeeMallOrderMapper.updateByPrimaryKeySelective(newBeeMallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (newBeeMallOrder != null) {
            //订单状态判断 非待支付状态下不进行修改操作
            if (newBeeMallOrder.getOrderStatus().intValue() != NewBeeMallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            newBeeMallOrder.setOrderStatus((byte) NewBeeMallOrderStatusEnum.ORDER_PAID.getOrderStatus());
            newBeeMallOrder.setPayType((byte) payType);
            newBeeMallOrder.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            newBeeMallOrder.setPayTime(new Date());
            newBeeMallOrder.setUpdateTime(new Date());
            if (newBeeMallOrderMapper.updateByPrimaryKeySelective(newBeeMallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public List<NewBeeMallOrderItemVO> getOrderItems(Long id) {
        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderMapper.selectByPrimaryKey(id);
        if (newBeeMallOrder != null) {
            List<NewBeeMallOrderItem> orderItems = newBeeMallOrderItemMapper.selectByOrderId(newBeeMallOrder.getOrderId());
            //获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<NewBeeMallOrderItemVO> newBeeMallOrderItemVOS = BeanUtil.copyList(orderItems, NewBeeMallOrderItemVO.class);
                return newBeeMallOrderItemVOS;
            }
        }
        return null;
    }

    /**
     * 保存 竞彩 订单
     *
     * @param lotteryOrderVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveLotteryOrder(LotteryOrderVO lotteryOrderVO) throws Exception {

        //生成 订单号
        String orderNo = NumberUtil.genOrderNo();
        lotteryOrderVO.setOrderNo(orderNo);
        lotteryOrderVO.setOrderType("LOTTERY");
        lotteryOrderVO.setIsDeleted((byte) 0);
        lotteryOrderVO.setPayStatus((byte) 0);
        lotteryOrderVO.setPayType((byte) 3);
        lotteryOrderVO.setOrderStatus((byte) 0);
        lotteryOrderVO.setUpdateTime(new Date());
        //校验 用户是否存在
        MallUser user = mallUserMapper.selectByPrimaryKey(lotteryOrderVO.getUserId());
        if (Objects.isNull(user)) {
            throw new Exception("用户不存在");
        }
        //校验玩法
        checkRule(lotteryOrderVO);

        //校验价格
        Integer price = getPrice(lotteryOrderVO);
        if (price != lotteryOrderVO.getTotalPrice()) {
            throw new Exception("价格不能对账！");
        }
        //计算中奖范围
        lotteryOrderVO.setWinPriceArray("" + getMinprize(lotteryOrderVO) + " ~ " + winMaxPrize(lotteryOrderVO) );
        //校验订单 至少要买一种
        if (CollectionUtils.isEmpty(lotteryOrderVO.getLotteryOrders())) {
            throw new Exception("至少买一种玩法！");
        }

        //订单（基础）
        newBeeMallOrderMapper.insert(lotteryOrderVO);
        //一对多
        //彩票订单
        if (!CollectionUtils.isEmpty(lotteryOrderVO.getLotteryOrders())) {
            lotteryOrderVO.getLotteryOrders().forEach(lotteryOrder -> {
                lotteryOrder.setId(UUID.randomUUID().toString());
                /*  lotteryOrder.setOrderDate(new Date());*/
                lotteryOrder.setOrderNo(orderNo);
            });
            lotteryOrderMapper.insertList(lotteryOrderVO.getLotteryOrders());
            lotteryOrderVO.getLotteryOrders().forEach(lotteryOrder -> {
                //一对一
                //胜负平（让）
                if (!CollectionUtils.isEmpty(lotteryOrder.getOddsOrders())) {
                    lotteryOrder.getOddsOrders().forEach(oddsOrder -> {
                        oddsOrder.setLotteryOrderId(lotteryOrder.getId());
                        oddsOrder.setId(UUID.randomUUID().toString());
                        oddsOrderMapper.insert(oddsOrder);
                    });

                }
                //总进球
                if (!CollectionUtils.isEmpty(lotteryOrder.getTtgOrders())) {
                    lotteryOrder.getTtgOrders().forEach(ttgOrder -> {
                        ttgOrder.setId(UUID.randomUUID().toString());
                        ttgOrder.setLotteryOrderId(lotteryOrder.getId());
                    });
                    ttgOrderMapper.insertList(lotteryOrder.getTtgOrders());
                }
                //半全场
                if (!CollectionUtils.isEmpty(lotteryOrder.getHalfCourtOrders())) {
                    lotteryOrder.getHalfCourtOrders().forEach(ele -> {
                        ele.setId(UUID.randomUUID().toString());
                        ele.setLotteryOrderId(lotteryOrder.getId());
                    });
                    halfCourtOrderMapper.insertList(lotteryOrder.getHalfCourtOrders());
                }
                //比分
                if (!CollectionUtils.isEmpty(lotteryOrder.getCrsOrders())) {
                    lotteryOrder.getCrsOrders().forEach(ele -> {
                        ele.setId(UUID.randomUUID().toString());
                        ele.setLotteryOrderId(lotteryOrder.getId());
                    });
                    crsOrderMapper.insertList(lotteryOrder.getCrsOrders());
                }
            });
        }

        //订单生成完成，通知管理员（店主） 打票
        Notice notice = new Notice();
        notice.setNoticeContent("用户: " + user.getLoginName() + ", 已经下单");
        notice.setRelateId(user.getUserId());
        notice.setType((byte) 1);
        noticeMapper.insert(notice);

        user.getRelateAdminId();
        // 扣除用户欢乐豆, 扣除管理员积分在上传图片时。
        UserHappyNum userHappyNum = userHappyNumMapper.selectOne(new QueryWrapper<UserHappyNum>().lambda().eq(UserHappyNum::getRelateId, user.getUserId()).select());
        if (Objects.isNull(userHappyNum) || userHappyNum.getHappyNum() <= 0) {
            throw new Exception("欢乐豆不足， 请联系管理员！");
        }
        userHappyNum.setHappyNum(userHappyNum.getHappyNum() - lotteryOrderVO.getTotalPrice());
        userHappyNumMapper.updateById(userHappyNum);

        return orderNo;
    }

    /**
     * 符合返回false
     * @return
     */
    private void checkRule(LotteryOrderVO lotteryOrderVO) throws Exception {

        switch (lotteryOrderVO.getGameType()) {
            case SCORE:
                return ;
            case VICTORY:
            case TOTAL_SCORE:
            case MIXED:
            case HALF_COURT: {
                String[] games = lotteryOrderVO.getRules().split(",");
                for (int i = 0; i < games.length; i++) {
                    if (!StringUtils.isEmpty(games[i])) {
                        if (Objects.nonNull(LotteryUtils.notSupportOneGame.get(games[i]))) {
                            throw new Exception(games[i].replaceAll("v", "串") + "不支持！");
                        }
                    }
                }

            }

        }
        return ;
    }

    private Integer getPrice(LotteryOrderVO lotteryOrderVO) throws Exception {
        int record = 0;//注数
        //获取玩法
        //3v1,2v1
        String[] games = lotteryOrderVO.getRules().split(",");
        for (int i = 0; i < games.length; i++) {
            if (!StringUtils.isEmpty(games[i])) {
                if (Integer.valueOf(games[i].split("v")[0]) <= lotteryOrderVO.getLotteryOrders().size()) {
                    //总游戏场数必须小于等于 玩法规则， 比如3串4 至少3场
                   Integer[] ways = LotteryUtils.getGames(games[i]);
                    int totalWay = Integer.valueOf(games[i].split("v")[1]);
                    int count = LotteryUtils.mathCmn(lotteryOrderVO.getLotteryOrders().size(), Integer.valueOf(games[i].split("v")[0]));
                    record = record + (totalWay * count);
                } else {
                    throw new Exception("场数必须大于等于购买玩法，比如3串4 至少3场");
                }

            }
        }
        //乘以倍数
        return record * lotteryOrderVO.getTimes() * 2;
    }

    //没有单场玩法的在matrix 里面不能有单场玩法
    private double getMinprize(LotteryOrderVO lotteryOrderVO) {
        //获取最小玩法
        String[] games = lotteryOrderVO.getRules().split(",");
        int minGame = 0;
        int index = 0;
        for (int i = 0; i < games.length; i++) {
            //
            if (!StringUtils.isEmpty(games[i])) {
                int buffer = Integer.valueOf(games[i].split("v")[0]);
                if (minGame == 0) {
                    minGame = buffer;
                } else {
                    if (minGame > buffer) {
                        minGame = buffer;
                        index = i;
                    }
                }
            }
        }
        // 一般没有单关， 没有单关就不包含单关的玩法表
        //获取最小的组合
        int minIndex = 0;
        Integer[] ways = LotteryUtils.matrixNumber.get(games[index]);
        for (int i = 0; i < ways.length; i++) {
            if (ways[i] > 0) {
                minIndex = i;
                break;
            }
        }
        List<Double> odds = LotteryUtils.getOddsByLotteryVos(lotteryOrderVO.getLotteryOrders());
        double winPrize = 1;
        for (int i = 0; i < minIndex + 1; i++) {
            winPrize = winPrize * odds.get(i);
        }
        return winPrize * 2;
    }

    /**
     * 最高奖金， 所有组合都中奖
     * @param lotteryOrderVO
     * @return
     */
    public double winMaxPrize(LotteryOrderVO lotteryOrderVO) {
        Vector<Double> odds = LotteryUtils.coverArrayListToVector(LotteryUtils.getOddsByLotteryVos(lotteryOrderVO.getLotteryOrders()));
        String[] games = lotteryOrderVO.getRules().split(",");
        double prize = 0.0;
        for (int i = 0; i < games.length; i++) { // 遍历玩法
            if (!StringUtils.isEmpty(games[i])) {
                Integer[] ways = LotteryUtils.getGames(games[i]); //eg: 3v1
                //获取 选择总比赛场次中的3场（3v1）
                Vector<Vector<Double>> subLine = LotteryUtils.subsets(odds, odds.size(), Integer.valueOf(games[i].split("v")[0]));
                for (Vector<Double> subList : subLine) {
                    //累计每关的奖励
                    for (int i1 = 0; i1 < ways.length; i1++) {
                        if (ways[i1] > 0) {
                            Vector<Vector<Double>> combines = LotteryUtils.subsets(subList, subList.size(), i1 + 1); //C n m
                            if (combines.size() > 0) {
                                double total = 0.0;
                                for (Vector<Double> doubles : combines) {
                                    AtomicReference<Double> count = new AtomicReference<>(1.0);
                                    doubles.forEach(aDouble -> {
                                        count.set(count.get() * aDouble);
                                    });
                                    total = total + count.get();
                                }
                                prize = prize + total;
                            } else {
                                System.out.println("can not find any vectors of cnm");
                            }
                        }

                    }
                }
            }

        }
        return prize * 2;
    }

    @Override
    public LotteryOrderVO getLotteryOrderByOrderNO(String orderNo) {
        ModelMapper modelMapper = new ModelMapper();

        NewBeeMallOrder newBeeMallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        LotteryOrderVO lotteryOrderVO = modelMapper.map(newBeeMallOrder, LotteryOrderVO.class);
        List<LotteryOrder> list = lotteryOrderMapper.selectList(new QueryWrapper<LotteryOrder>().eq("order_no", orderNo));
        list.forEach(lotteryOrder -> {
            switch (lotteryOrder.getType()) {
                case HALF_COURT:
                    List<HalfCourtOrder> halfCourtOrders = halfCourtOrderMapper.selectList(new QueryWrapper<HalfCourtOrder>().eq("lottery_order_id", lotteryOrder.getId()));
                    lotteryOrder.setHalfCourtOrders(halfCourtOrders);
                    break;
                case TOTAL_SCORE:
                    List<TtgOrder> ttgOrders = ttgOrderMapper.selectList(new QueryWrapper<TtgOrder>().eq("lottery_order_id", lotteryOrder.getId()));
                    lotteryOrder.setTtgOrders(ttgOrders);
                    break;
                case SCORE:
                    List<CrsOrder> crsOrders = crsOrderMapper.selectList(new QueryWrapper<CrsOrder>().eq("lottery_order_id", lotteryOrder.getId()));
                    lotteryOrder.setCrsOrders(crsOrders);
                    break;
                case MIXED:
                    List<HalfCourtOrder> halfCourtOrdersMixed = halfCourtOrderMapper.selectList(new QueryWrapper<HalfCourtOrder>().eq("lottery_order_id", lotteryOrder.getId()));
                    lotteryOrder.setHalfCourtOrders(halfCourtOrdersMixed);
                    List<TtgOrder> ttgOrdersMixed = ttgOrderMapper.selectList(new QueryWrapper<TtgOrder>().eq("lottery_order_id", lotteryOrder.getId()));
                    lotteryOrder.setTtgOrders(ttgOrdersMixed);
                    List<OddsOrder> oddsOrdersMixed = oddsOrderMapper.selectList(new QueryWrapper<OddsOrder>().eq("lottery_order_id", lotteryOrder.getId()));
                    lotteryOrder.setOddsOrders(oddsOrdersMixed);
                    List<CrsOrder> crsOrdersMixed = crsOrderMapper.selectList(new QueryWrapper<CrsOrder>().eq("lottery_order_id", lotteryOrder.getId()));
                    lotteryOrder.setCrsOrders(crsOrdersMixed);
                    break;
                case VICTORY:
                    List<OddsOrder> oddsOrders = oddsOrderMapper.selectList(new QueryWrapper<OddsOrder>().eq("lottery_order_id", lotteryOrder.getId()));
                    lotteryOrder.setOddsOrders(oddsOrders);
                    break;
            }
        });
        lotteryOrderVO.setLotteryOrders(list);
        //查看图片
        List<ImgOrder> imgOrders = imgOrderMapper.selectList(new QueryWrapper<ImgOrder>().eq("deleted", 0).eq("lottery_order_id", newBeeMallOrder.getOrderNo()));
        lotteryOrderVO.setImgOrders(imgOrders);
        return lotteryOrderVO;
    }

    @Override
    public PageCL<LotteryOrder> getLotteryOrder(int pageNo, int pageSize, Date start, Date end) {
        PageHelper.startPage(pageNo, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper<LotteryOrder>();
        if (Objects.nonNull(start) && Objects.nonNull(end)) {
            queryWrapper.lambda().between(true, "order_date", start, end);
        } else {
            queryWrapper.select();
        }
        List<LotteryOrder> list = lotteryOrderMapper.selectList(queryWrapper);

        PageCL<LotteryOrder> pageInfo = new PageCL<>();
        PageInfo<LotteryOrder> dbInfo = new PageInfo<>(list);
        pageInfo.setPageCount(dbInfo.getPages());
        pageInfo.setTotal(Integer.parseInt(String.valueOf(dbInfo.getTotal())));
        pageInfo.setPageNum(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(pageSize);
        pageInfo.setList(dbInfo.getList());
        return pageInfo;
    }
}