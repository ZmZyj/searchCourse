package com.scratch.wechat.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scratch.wechat.common.JdbcUtil;
import com.scratch.wechat.entity.Article;

public class DbSaver {

	private static Connection conn = null;

	private static PreparedStatement prest = null;

	private static Logger logger = LoggerFactory.getLogger(DbSaver.class);

	public static void saveArticlesToDb(List<Article> list) {
		DbSaver ds = new DbSaver();
		try {
			ds.saveArticle(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveArticle(List<Article> list) throws Exception {
		conn = JdbcUtil.getConnection();
		if (conn == null) {
			throw new Exception("数据库连接失败！");
		}

		try {
			conn.setAutoCommit(false);
			String sql = "insert ignore into article(articleid,title,url,tag)values(?,?,?,?)";
			prest = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for (int i = 0; i < list.size(); i++) {
				Article a = list.get(i);
				prest.setString(1, a.getArticleid());
				prest.setString(2, a.getTitle());
				prest.setString(3, a.getUrl());
				prest.setString(4, a.getTag());
				prest.addBatch();
			}
			prest.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
		} catch (SQLException e) {
			logger.info(e.getMessage());
            e.printStackTrace();
       } catch (Exception e) {
            e.printStackTrace();
       } finally {
            try {
                 conn.close();
            } catch (SQLException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
            }
       }

	}

}
