package com.galaxy.portal.elasticsearch.service;


import com.alibaba.fastjson.JSON;
import com.galaxy.portal.elasticsearch.entiy.ElasticEntity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class BaseElasticService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * @param idxName 索引名称
     * @param idxSQL  索引描述
     * @return void
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:30
     * @since
     */
    public void createIndex(String idxName, String idxSQL) {
        try {
            if (!this.indexExist(idxName)) {
                log.error(" idxName={} 已经存在,idxSql={}", idxName, idxSQL);
                return;
            }
            CreateIndexRequest request = new CreateIndexRequest(idxName);
            buildSetting(request);
            request.mapping(idxSQL, XContentType.JSON);

            request.settings(Settings.builder().put("refresh_interval", "120s")); //手工指定Setting
            CreateIndexResponse res = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            if (!res.isAcknowledged()) {
                throw new RuntimeException("初始化失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 制定配置项的判断索引是否存在，注意与 isExistsIndex 区别
     *
     * @param idxName index名
     * @return boolean
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:27
     * @since
     */
    public boolean indexExist(String idxName) throws Exception {
        GetIndexRequest request = new GetIndexRequest(idxName);
        //TRUE-返回本地信息检索状态，FALSE-还是从主节点检索状态
        request.local(false);
        //是否适应被人可读的格式返回
        request.humanReadable(true);
        //是否为每个索引返回所有默认设置
        request.includeDefaults(false);
        //控制如何解决不可用的索引以及如何扩展通配符表达式,忽略不可用索引的索引选项，仅将通配符扩展为开放索引，并且不允许从通配符表达式解析任何索引
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }


    /**
     * 判断某个index是否存在
     *
     * @param idxName index名
     * @return boolean
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:27
     * @since
     */
    public boolean isExistsIndex(String idxName) throws Exception {
        return restHighLevelClient.indices().exists(new GetIndexRequest(idxName), RequestOptions.DEFAULT);
    }

    /**
     * 设置分片
     *
     * @param request
     * @return void
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 19:27
     * @since
     */
    public void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));
    }

    /**
     * @param idxName index
     * @param entity  对象
     * @return void
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:27
     * @since
     */
    public void insertOrUpdateOne(String idxName, ElasticEntity entity) {
        IndexRequest request = new IndexRequest(idxName);
        log.error("Data : id={},entity={}", entity.getId(), JSON.toJSONString(entity.getData()));
        request.id(entity.getId());
//        request.source(entity.getData(), XContentType.JSON);
        request.source(JSON.toJSONString(entity.getData()), XContentType.JSON);
        try {
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param idxName index
     * @param entity  对象
     * @return void
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:27
     * @since
     */
    public void deleteOne(String idxName, ElasticEntity entity) {
        DeleteRequest request = new DeleteRequest(idxName);
        request.id(entity.getId());
        try {
            restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量插入数据
     *
     * @param idxName index
     * @param list    带插入列表
     * @return void
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:26
     * @since
     */
    public void insertBatch(String idxName, List<ElasticEntity> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(idxName).id(item.getId())
                .source(JSON.toJSONString(item.getData()), XContentType.JSON)));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量插入数据
     *
     * @param idxName index
     * @param list    带插入列表
     * @return void
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:26
     * @since
     */
    public void insertBatchTrueObj(String idxName, List<ElasticEntity> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(idxName).id(item.getId())
                .source(item.getData(), XContentType.JSON)));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除
     *
     * @param idxName index
     * @param idList  待删除列表
     * @return void
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:14
     * @since
     */
    public <T> void deleteBatch(String idxName, Collection<T> idList) {
        BulkRequest request = new BulkRequest();
        idList.forEach(item -> request.add(new DeleteRequest(idxName, item.toString())));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param idxName index
     * @param builder 查询参数
     * @param c       结果类对象
     * @return java.util.List<T>
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:14
     * @since
     */
    public <T> List<T> search(String idxName, SearchSourceBuilder builder, Class<T> c) {
        SearchRequest request = new SearchRequest(idxName);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), c));
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除index
     *
     * @param idxName
     * @return void
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:13
     * @since
     */
    public void deleteIndex(String idxName) {
        try {
            if (!this.indexExist(idxName)) {
                log.error(" idxName={} 已经存在", idxName);
                return;
            }
            restHighLevelClient.indices().delete(new DeleteIndexRequest(idxName), RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param idxName
     * @param builder
     * @return void
     * @throws
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/10/17 17:13
     * @since
     */
    public void deleteByQuery(String idxName, QueryBuilder builder) {

        DeleteByQueryRequest request = new DeleteByQueryRequest(idxName);
        request.setQuery(builder);
        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean indexContainField(String index, String field) throws IOException {

        AtomicBoolean flag = new AtomicBoolean(false);
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(index);
        GetMappingsResponse response = restHighLevelClient.indices().getMapping(request, RequestOptions.DEFAULT);
        final Map<String, MappingMetaData> allMappings = response.mappings();
        List<String> fieldList = new ArrayList();
        for (Map.Entry<String, MappingMetaData> indexValue : allMappings.entrySet()) {
            Map<String, Object> mapping = indexValue.getValue().sourceAsMap();
            Iterator<Map.Entry<String, Object>> entries = mapping.entrySet().iterator();
            entries.forEachRemaining(stringObjectEntry -> {
                if (stringObjectEntry.getKey().equals("properties")) {
                    Map<String, Object> value = (Map<String, Object>) stringObjectEntry.getValue();
                    for (Map.Entry<String, Object> ObjectEntry : value.entrySet()) {
                        /*Map<String, Object> map = new HashMap<>();
                        String key = ObjectEntry.getKey();
                        Map<String, Object> value1 = (Map<String, Object>) ObjectEntry.getValue();
                        map.put(key, value1.get("type"));*/
                        String key = ObjectEntry.getKey();
                        if (key.equals(field)) {
                            flag.set(true);
                        }
//                        fieldList.add(key);

                    }
                }
            });
        }

        return flag.get();

    }


    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isMap(Object obj) {
        if (null == obj) {
//            throw new NullPointerException("Object check for isArray is null");
            return false;
        }
//        反射 获得类型
        return obj instanceof Map;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> inMap = new HashMap<>();
        inMap.put("key1", "key1");
        inMap.put("key2", "key2");
        inMap.put("key3", "key3");
        inMap.put("key4", "key4");
        inMap.put("key5", "key5");
        map.put("map1", inMap);
        Object map1 = map.get("map1");
        System.out.println("Obj is Map:" + isMap(map1));

    }
}
