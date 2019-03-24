package com.yl;

import static org.junit.Assert.assertTrue;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void demo1() throws IOException {
        // 创建连接搜索服务器对象
        Client client = TransportClient
                .builder()
                .build()
                .addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        // 描述json 数据
        /*
         * {id:xxx, title:xxx, content:xxx}
         */
        XContentBuilder builder = XContentFactory
                .jsonBuilder()
                .startObject()
                .field("id66", 6)
                .field("title", "ElasticSearch是一个基于Lucene的搜索服务器")
                .field("content",
                        "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。")
                .endObject();

        XContentBuilder builder2 = XContentFactory
                .jsonBuilder()
                .startObject()
                .field("id2", 9)
                .field("title2", "ElasticSearch是一个基于Lucene的搜索服务器")
                .field("content2",
                        "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。")
                .endObject();

        // 建立文档对象
        client.prepareIndex("blog1", "article", "4").setSource(builder).get();
        client.prepareIndex("blog1", "comment", "8").setSource(builder2).get();

        // 关闭连接
        client.close();
    }
}
