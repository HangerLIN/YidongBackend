package vip.xiaonuo.biz.modular.New.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.biz.modular.New.message.entity.Message;
import vip.xiaonuo.biz.modular.New.message.service.MessageService;
import vip.xiaonuo.biz.modular.New.message.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【Message】的数据库操作Service实现
* @createDate 2024-08-21 19:52:33
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

}




