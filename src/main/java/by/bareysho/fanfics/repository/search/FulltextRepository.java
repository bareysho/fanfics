package by.bareysho.fanfics.repository.search;

import by.bareysho.fanfics.model.Fanfic;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class FulltextRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Fanfic> search(String text) {
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Fanfic.class).get();
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields("fanficName",
                                "description",
                                "chapters.chapterName",
                                "tags.tagName",
                                "comments.text",
                                "genres.genreName")
                        .matching(text)
                        .createQuery();
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Fanfic.class);
        @SuppressWarnings("unchecked")
        List<Fanfic> results = jpaQuery.getResultList();
        return results;
    }
}
