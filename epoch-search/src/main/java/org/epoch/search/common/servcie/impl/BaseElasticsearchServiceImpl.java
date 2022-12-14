package org.epoch.search.common.servcie.impl;


import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.epoch.search.common.servcie.BaseElasticsearchService;
import org.epoch.search.common.util.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * @author Marshal
 * @date 2019/9/24
 * @desc
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseElasticsearchServiceImpl<T> implements BaseElasticsearchService<T> {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Override
    public void createIndex(Class clazz) {
        elasticsearchTemplate.createIndex(clazz);
    }

    @Override
    public void deleteIndex(Class clazz) {
        elasticsearchTemplate.deleteIndex(clazz);
    }

    @Override
    public void putMapping(Class clazz) {
        elasticsearchTemplate.putMapping(clazz);
    }

    @Override
    public List<T> query(String keyword, Class<T> clazz) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(new QueryStringQueryBuilder(keyword))
                .withSort(SortBuilders.scoreSort().order(SortOrder.ASC))
                .build();
        return null;
    }

    @Override
    public Map<String, Object> queryHighlight(String keyword, String indexName, String... fieldNames) {
        // ??????????????????,?????????????????????.
        QueryBuilder matchQuery = ElasticSearchUtil.getQueryBuilder(keyword, fieldNames);

        // ????????????,???????????????highlighter?????????
        HighlightBuilder highlightBuilder = ElasticSearchUtil.getHighlightBuilder(fieldNames);


        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery)
                .withHighlightBuilder(highlightBuilder).build();


        // ??????????????????
//        AggregatedPage<SysUser> list = elasticsearchTemplate.queryForPage(searchQuery, SysUser.class);

//        Aggregations aggregations = list.getAggregations();

        // ??????????????????

        return null;
    }

    @Override
    public Map<String, Object> queryHighlight(int pageNo, int pageSize, String keyword, String indexName, String... fieldNames) {
        return queryHighlight(pageNo, pageSize, null, keyword, indexName, fieldNames);
    }

    @Override
    public Map<String, Object> queryHighlight(int pageNo, int pageSize, String termWord, String keyword, String indexName, String... fieldNames) {
        // ??????????????????,?????????????????????
        QueryBuilder matchQuery;
        if (!ObjectUtils.isEmpty(termWord)) {
            matchQuery = ElasticSearchUtil.getQueryBuilder(termWord, keyword, fieldNames);
        } else {
            matchQuery = ElasticSearchUtil.getQueryBuilder(keyword, fieldNames);
        }

        // ????????????,???????????????highlighter?????????
        HighlightBuilder highlightBuilder = ElasticSearchUtil.getHighlightBuilder(fieldNames);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery)
                .withHighlightBuilder(highlightBuilder).build();


        // ??????????????????
//        AggregatedPage<SysUser> list = elasticsearchTemplate.queryForPage(searchQuery, SysUser.class);

//        Aggregations aggregations = list.getAggregations();

        // ??????????????????
//        SearchResponse response = elasticsearchTemplate.getClient().prepareSearch(indexName)
//                .setQuery(matchQuery)
//                .highlighter(highlightBuilder)
//                .setFrom((pageNo - 1) * pageSize)
//                .setSize(pageNo * pageSize)
//                .get();
//
//        // ??????????????????
//        SearchHits hits = response.getHits();
//
//        Map<String, Object> result = new HashMap<>(2);
//        result.put("totalCount", hits.getTotalHits());
//        result.put("rows", ElasticSearchUtil.getHitList(hits));
//        return result;
        return null;
    }
}

