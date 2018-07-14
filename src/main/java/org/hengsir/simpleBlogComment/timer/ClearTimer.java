package org.hengsir.simpleBlogComment.timer;

import org.hengsir.simpleBlogComment.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author hengsir
 * @date 2018/7/13 下午4:07
 */
@Service
public class ClearTimer {
    @Autowired
    private CommentDao commentDao;

    /**
     * 每天0点把记录删除
     */
    @Scheduled(cron = "59 59 23 * * ?")
    public void recordToHist(){
        commentDao.clearToday();
    }
}
