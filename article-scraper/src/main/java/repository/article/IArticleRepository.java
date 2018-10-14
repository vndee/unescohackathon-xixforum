package repository.article;

import common.model.Article;

public interface IArticleRepository {
    int Insert(Article article);
    int Update(Article old_article, Article new_article);
}
