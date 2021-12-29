package com.cloud.service.elasticsearch;

import com.cloud.service.ServiceConsumersApplication;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: ElasticSearchTest
 * @description: 学习地址
 * https://www.cnblogs.com/huqi96/p/14881593.html
 * https://juejin.cn/post/6871957655553769485#heading-11
 * https://blog.csdn.net/zwc953685177/article/details/120276845
 * ElasticSearch添加索引 https://www.cnblogs.com/huanshilang/p/12616310.html
 * elasticsearch对已存在的索引增加mapping字段 https://www.cnblogs.com/tjp40922/p/13090419.html
 * text类型可以分词,keyword不能分词
 * @date 2021/12/28 10:07
 */
@SpringBootTest(classes = ServiceConsumersApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticSearchTest {
    @Autowired
    RestHighLevelClient remoteHighLevelClient;

    //--------------------------------hero索引-----------------------------

    /**
     * @description: 创建索引, 添加数据
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/28 10:09
     */
    @Test
    public void createData() throws Exception {
        IndexRequest request = new IndexRequest("hero");
        request.id("1");
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "曹操");
        map.put("country", "魏");
        map.put("birthday", "公元155年");
        map.put("longevity", "65");
        request.source(map);
        IndexResponse indexResponse = remoteHighLevelClient.index(request, RequestOptions.DEFAULT);
        /*assertEquals(DocWriteResponse.Result.CREATED, indexResponse.getResult());
        assertEquals(1, version);*/
        System.out.println(indexResponse);
    }

    /**
     * @description: 批量插入
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/28 10:48
     */
    @Test
    public void bulkRequestTest() throws Exception {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("hero").id("2")
                .source(XContentType.JSON, "id", "2", "name", "刘备", "country", "蜀", "birthday", "公元161年", "longevity", "61"));
        request.add(new IndexRequest("hero").id("3")
                .source(XContentType.JSON, "id", "3", "name", "孙权", "country", "吴", "birthday", "公元182年", "longevity", "61"));
        request.add(new IndexRequest("hero").id("4")
                .source(XContentType.JSON, "id", "4", "name", "诸葛亮", "country", "蜀", "birthday", "公元181年", "longevity", "53"));
        request.add(new IndexRequest("hero").id("5")
                .source(XContentType.JSON, "id", "5", "name", "司马懿", "country", "魏", "birthday", "公元179年", "longevity", "72"));
        request.add(new IndexRequest("hero").id("6")
                .source(XContentType.JSON, "id", "6", "name", "荀彧", "country", "魏", "birthday", "公元163年", "longevity", "49"));
        request.add(new IndexRequest("hero").id("7")
                .source(XContentType.JSON, "id", "7", "name", "关羽", "country", "蜀", "birthday", "公元160年", "longevity", "60"));
        request.add(new IndexRequest("hero").id("8")
                .source(XContentType.JSON, "id", "8", "name", "周瑜", "country", "吴", "birthday", "公元175年", "longevity", "35"));
        BulkResponse bulk = remoteHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        //assertFalse(bulk.hasFailures());
        System.out.println(bulk.hasFailures());
    }

    /**
     * @description: 修改
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/28 11:04
     */
    @Test
    public void updateTest() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("country", "魏");
        UpdateRequest request = new UpdateRequest("hero", "7").doc(jsonMap);
        UpdateResponse updateResponse = remoteHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(DocWriteResponse.Result.UPDATED + "\n" + updateResponse.getResult());
    }

    /**
     * @description: 插入/更新数据
     * 这里使用方法index()我们可以轻松的实现创建索引、插入数据、更新数据于一体，当指定的索引不存在时即创建索引，
     * 当数据不存在时就插入，数据存在时就更新
     * @param:
     * @return: void
     * @author: xjh
     */
    @Test
    public void insertOrUpdateOne() throws Exception {
        IndexRequest request = new IndexRequest("hero");
        request.id("1");
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "曹操");
        map.put("country", "魏");
        map.put("birthday", "公元155年");
        map.put("longevity", "66");
        request.source(map);
        IndexResponse indexResponse = remoteHighLevelClient.index(request, RequestOptions.DEFAULT);   //  1
        System.out.println(DocWriteResponse.Result.UPDATED);
    }

    /**
     * @description: 根据id删除
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/28 11:15
     */
    @Test
    public void deleteByIdTest() throws Exception {
        DeleteRequest request = new DeleteRequest("hero");
        request.id("1");
        DeleteResponse deleteResponse = remoteHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.getResult());
    }

    /**
     * @description: 根据条件删除
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/28 11:21
     */
    @Test
    public void deleteByQueryRequestTest() throws Exception {
        DeleteByQueryRequest request = new DeleteByQueryRequest("hero");
        request.setConflicts("proceed");
        request.setQuery(new TermQueryBuilder("country", "吴"));
        BulkByScrollResponse bulkResponse = remoteHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
    }

    /**
     * @description: 复合操作
     * 我们使用了BulkRequest对象，将DeleteRequest、UpdateRequest两种操作add到BulkRequet中，
     * 然后将返回的BulkItemResponse[]数组根据不同的操作类型进行分类处理即可
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/28 11:29
     */
    @Test
    public void bulkDiffRequestTest() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest("hero", "3"));
        request.add(new UpdateRequest("hero", "7")
                .doc(XContentType.JSON, "longevity", "70"));
        BulkResponse bulkResponse = remoteHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        BulkItemResponse[] bulkItemResponses = bulkResponse.getItems();
        for (BulkItemResponse item : bulkItemResponses) {
            DocWriteResponse itemResponse = item.getResponse();
            switch (item.getOpType()) {
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
            }
            System.out.println(item.status());
        }
    }

    /**
     * @description: 单条件查询 + limit
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/28 11:33
     */
    @Test
    public void selectByUserTest() throws Exception {
        SearchRequest request = new SearchRequest("hero");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(new TermQueryBuilder("country", "魏"));
        // 相当于mysql里边的limit 1；
        builder.size(1);
        request.source(builder);
        SearchResponse searchResponse = remoteHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (int i = 0; i < searchHits.length; i++) {
            System.out.println(searchHits[i].getSourceAsString());
        }
    }

    /**
     * @description: 多条件查询 + 排序 + 分页
     * 将曹魏集团的寿命50岁以上的英雄查询出来，并根据寿命从高到低排序，只截取两位英雄，其对应的sql：
     * select * from hero where country='魏' and longevity >= 50 order by longevity DESC limit 2;
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/28 15:10
     */
    @Test
    public void boolQueryTest() throws Exception {
        SearchRequest request = new SearchRequest("hero");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 不会对搜索词进行分词处理，而是作为一个整体与目标字段进行匹配，若完全匹配，则可查询到
        //mustNot的使用（mustMot不能包含的查询条件）
        //QueryBuilders.wildcardQuery对分词进行模糊匹配 QueryBuilders.wildcardQuery("name","*胡*"));
        boolQueryBuilder.must(new TermQueryBuilder("country", "魏"));
        // RangeQueryBuilder范围查询，对age进行范围查询
        boolQueryBuilder.must(new RangeQueryBuilder("longevity").gte(50));
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.from(2).size(2);
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.sort("longevity", SortOrder.DESC);
        request.source(sourceBuilder);
        SearchResponse searchResponse = remoteHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (int i = 0; i < searchHits.length; i++) {
            System.out.println(searchHits[i].getSourceAsString());
        }
    }

    /**
     * 模糊高亮搜索
     *
     * @description: 最佳实践:  fuzziness 在绝大多数场合都应该设置成 AUTO
     * 如果不设置 fuziness 参数，查询是精确匹配的。
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/28 16:06
     */
    @Test
    public void highlightedQueryTest() throws Exception {
        //定义索引库
        SearchRequest request = new SearchRequest("hero");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //定义query模糊查询
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("birthday", "公元*");
        //定义高亮查询
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //设置需要高亮的字段
        highlightBuilder.field("birthday")
                // 设置前缀、后缀
                .preTags("<font color='red'>")
                .postTags("</font>");
        sourceBuilder.query(queryBuilder);
        sourceBuilder.highlighter(highlightBuilder);
        request.source(sourceBuilder);
        SearchResponse search = remoteHighLevelClient.search(request, RequestOptions.DEFAULT);
        List<Map<String, Object>> list = new ArrayList<>();
        //遍历高亮结果
        for (SearchHit hit : search.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField nameHighlight = highlightFields.get("birthday");
            //原始数据
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //高亮的字段替换
            if (null != nameHighlight) {
                Text[] fragments = nameHighlight.getFragments();
                String birthday = "";
                for (Text text : fragments) {
                    birthday += text;
                }
                sourceAsMap.put("birthday", birthday);
                list.add(sourceAsMap);
            }
        }
        list.stream().forEach(System.out::println);
    }

    //-----------------------------student索引-----------------------------

    /**
     * @description: 批量添加student
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/29 10:28
     */
    @Test
    public void bulkInsertByStudentTest() throws Exception {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("student").id("1").source(XContentType.JSON, "id", "1", "age", "2", "name", "张三", "class", "一班"));
        request.add(new IndexRequest("student").id("2").source(XContentType.JSON, "id", "2", "age", "3", "name", "张三丰", "class", "二班"));
        request.add(new IndexRequest("student").id("3").source(XContentType.JSON, "id", "3", "age", "4", "name", "张三疯", "class", "三班"));
        request.add(new IndexRequest("student").id("4").source(XContentType.JSON, "id", "4", "age", "5", "name", "李四", "class", "一班"));
        request.add(new IndexRequest("student").id("5").source(XContentType.JSON, "id", "5", "age", "6", "name", "王五", "class", "三班"));
        BulkResponse bulk = remoteHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulk.status());
    }

    /**
     * @description: ik高亮分词查询
     * text类型可以分词,keyword不能分词
     * @param:
     * @return: void
     * @author: xjh
     * @date: 2021/12/29 10:32
     */
    @Test
    public void highlightedIKQueryTest() throws Exception {
        SearchRequest request = new SearchRequest("student");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "张三");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name").preTags("<font color='red'>").postTags("</font>");
        sourceBuilder.query(queryBuilder);
        sourceBuilder.highlighter(highlightBuilder);
        request.source(sourceBuilder);
        SearchResponse search = remoteHighLevelClient.search(request, RequestOptions.DEFAULT);
        List<Map<String, Object>> list = new ArrayList<>();
        //遍历高亮结果
        for (SearchHit hit : search.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField nameHighlight = highlightFields.get("name");
            //原始数据
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //高亮的字段替换
            if (null != nameHighlight) {
                Text[] fragments = nameHighlight.getFragments();
                String name = "";
                for (Text text : fragments) {
                    name += text;
                }
                sourceAsMap.put("name", name);
                list.add(sourceAsMap);
            }
        }
        list.stream().forEach(System.out::println);
    }
}
