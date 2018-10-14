package repository.article;

import common.helper.ExceptionNotification;
import common.model.Article;
import database.MysqlConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArticleRepository extends MysqlConnector implements IArticleRepository {

    // ctor
    public ArticleRepository () {
        // this.table_name = Article.class.getSimpleName();
        this.table_name = "Articles";
    }
    @Override
    public int Insert(Article article) {
        String sql_insert = "INSERT INTO Articles(title, html_text, source, encoded_source, updated_date) " +
                "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, article.title);
            statement.setString(2, article.html_text);
            statement.setString(3, article.source);
            statement.setString(4, article.encoded_source);
            statement.setLong(5, article.updated_date);

            // execute query
            // execute statement
            statement.executeUpdate();

            // get id
            ResultSet rs = statement.getGeneratedKeys();


            // return id
            int last_inserted_id = 0;
            if(rs.next()) {
                last_inserted_id = rs.getInt(1);

            }

            // close resultset and statement
            rs.close();
            statement.close();

            // return last inserted id
            return last_inserted_id;
        } catch (SQLException ex) {
            ExceptionNotification.printException(ex);
            return 0;
        }

    }

    @Override
    public int Update(Article old_article, Article new_article) {
        return 0;
    }
}
