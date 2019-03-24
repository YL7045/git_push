package cn.itcast.elasticsearch.test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

import cn.itcast.elasticsearch.domain.Article;

//import com.fasterxml.jackson.databind.ObjectMapper;


// ElasticSearch 测试程序 
public class ElasticSearchTest {

	// 直接在ElasticSearch中建立文档，自动创建索引
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

	
//	boolQuery()         布尔查询，可以用来组合多个查询条件
//	fuzzyQuery()        相似度查询
//	matchAllQuery()     查询所有数据
//	regexQuery()        正则表达式查询
//	termQuery()        词条查询
//	wildcardQuery()      模糊查询
	// 搜索在elasticSearch中创建文档对象
	/*@Test
	public void demo2() throws IOException {
		// 创建连接搜索服务器对象
		Client client = TransportClient
				.builder()
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("localhost"), 9300));
		// 搜索数据
		// get() 相当于 execute().actionGet()
		SearchResponse searchResponse = client.prepareSearch("bogl1")
				.setTypes("comment").setQuery(QueryBuilders.matchAllQuery())
				.get();
		printSearchResponse(searchResponse);

		// 关闭连接
		client.close();
	}*/
	
	/*private void printSearchResponse(SearchResponse searchResponse) {
		SearchHits hits = searchResponse.getHits(); // 获取命中次数，查询结果有多少对象
		System.out.println("查询结果有：" +hits.getHits().length + "条"+" 总条数="+hits.getTotalHits());
		Iterator<SearchHit> iterator = hits.iterator();
		while (iterator.hasNext()) {
			SearchHit searchHit = iterator.next(); // 每个查询对象
			System.out.println(searchHit.getSourceAsString()); // 获取字符串格式打印
			System.out.println("title2:" + searchHit.getSource().get("title2"));
		}
	}
*/
	// 各种查询使用
	/*@Test
	public void demo3() throws IOException {
		// 创建连接搜索服务器对象
		Client client = TransportClient
				.builder()
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("127.0.0.1"), 9300));
		// 搜索数据
		// get() === execute().actionGet()
//		 SearchResponse searchResponse = client.prepareSearch("blog1")
//		 .setTypes("comment")
//		 .setQuery(QueryBuilders.queryStringQuery("分花草")).get();

		 SearchResponse searchResponse = client.prepareSearch("blog2")
		 .setTypes("article")
		 .setQuery(QueryBuilders.wildcardQuery("content", "*服器*")).get(); 

//		SearchResponse searchResponse = client.prepareSearch("blog2")
//				.setTypes("article")
//				.setQuery(QueryBuilders.termQuery("content", "方案")).get();
		
		printSearchResponse(searchResponse);

		// 关闭连接
		client.close();
	}
*/
	// 索引操作
	/*@Test
	public void demo4() throws IOException {
		// 创建连接搜索服务器对象
		Client client = TransportClient
				.builder()
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("127.0.0.1"), 9300));

		// 创建索引
		client.admin().indices().prepareCreate("blog2").get();

		// 删除索引
//		client.admin().indices().prepareDelete("blog2").get();

		// 关闭连接
		client.close();
	}
*/
	// 映射操作
	/*@Test
	public void demo5() throws IOException, InterruptedException,
			ExecutionException {
		// 创建连接搜索服务器对象
		Client client = TransportClient
				.builder()
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("127.0.0.1"), 9300));

		// 添加映射
		XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject()
					.startObject("article")
						.startObject("properties")
							.startObject("id")
								.field("type", "integer")
								.field("store", "yes")
							.endObject()
							.startObject("title")
								.field("type", "string")
								.field("store", "yes")
								.field("analyzer", "ik")
							.endObject()
							.startObject("content")
								.field("type", "string")
								.field("store", "yes")
								.field("analyzer", "ik")
							.endObject()
						.endObject()
					.endObject()
				.endObject();
		PutMappingRequest mapping = Requests.putMappingRequest("blog2")
				.type("article").source(builder);
		client.admin().indices().putMapping(mapping).get();
		
		// 关闭连接
		client.close();
	}*/

	// 文档相关操作
	/*@Test
	public void demo6() throws IOException, InterruptedException,
			ExecutionException {
		// 创建连接搜索服务器对象
		Client client = TransportClient
				.builder()
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("127.0.0.1"), 9300));
		// 描述json 数据
		*//*
		 * {id:xxx, title:xxx, content:xxx}
		 *//*
		Article article = new Article();
		article.setId(2);
		article.setTitle("开发工作更加快乐哟");
		article.setContent("我们希望我们的搜索解决方案要快，我们希望有一个零配置和一个完全免费的搜索模式，"
				+ "我们希望能够简单地使用JSON通过HTTP的索引数据，我们希望我们的搜索服务器始终可用，"
				+ "我们希望能够一台开始并扩展到数百，我们要实时搜索，我们要简单的多租户，"
				+ "我们希望建立一个云的解决方案。Elasticsearch旨在解决所有这些问题和更多的问题。");

		ObjectMapper objectMapper = new ObjectMapper();

		// 建立文档
//		 client.prepareIndex("blog2", "article", article.getId().toString())
//		 .setSource(objectMapper.writeValueAsString(article)).get();

		// 修改文档
		 client.prepareUpdate("blog2", "article", article.getId().toString())
		 .setDoc(objectMapper.writeValueAsString(article)).get();

		// 修改文档
//		 client.update(
//		 new UpdateRequest("blog2", "article", article.getId().toString())
//		 .doc(objectMapper.writeValueAsString(article))).get();

		// 删除文档
//		 client.prepareDelete("blog2", "article", article.getId().toString())
//		 .get();

		// 删除文档
//		client.delete(
//				new DeleteRequest("blog2", "article", article.getId()
//						.toString())).get();

		// 关闭连接
		client.close();
	}*/

	// 批量插入100条记录
	/*@Test
	public void demo7() throws IOException, InterruptedException,
			ExecutionException {
		// 创建连接搜索服务器对象
		Client client = TransportClient
				.builder()
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("127.0.0.1"), 9300));

		ObjectMapper objectMapper = new ObjectMapper();

		for (int i = 1; i <= 100; i++) {
			// 描述json 数据
			Article article = new Article();
			article.setId(i);
			article.setTitle(i + "搜索工作其实很快乐");
			article.setContent(i
					+ "我们希望我们的搜索解决方案要快，我们希望有一个零配置和一个完全免费的搜索模式，我们希望能够简单地使用JSON通过HTTP的索引数据，我们希望我们的搜索服务器始终可用，我们希望能够一台开始并扩展到数百，我们要实时搜索，我们要简单的多租户，我们希望建立一个云的解决方案。Elasticsearch旨在解决所有这些问题和更多的问题。");

			// 建立文档
			client.prepareIndex("blog2", "article", article.getId().toString())
					.setSource(objectMapper.writeValueAsString(article)).get();
		}
		// 关闭连接
		client.close();
	}*/

	// 分页搜索
/*	@Test
	public void demo8() throws IOException {
		// 创建连接搜索服务器对象
		Client client = TransportClient
				.builder()
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("127.0.0.1"), 9300));
		
//		 SearchResponse searchResponse = client.prepareSearch("blog2")
//				 .setTypes("article")
//				 .setQuery(QueryBuilders.queryStringQuery("文")).get();
		 
		// 搜索数据
		// get() === execute().actionGet()
		SearchRequestBuilder searchRequestBuilder = client
				.prepareSearch("blog2").setTypes("article")
				.setQuery(QueryBuilders.matchAllQuery());
		
		// 查询第1页数据，每页20条
		searchRequestBuilder.setFrom(0).setSize(20);
		SearchResponse searchResponse = searchRequestBuilder.get();
				
		printSearchResponse(searchResponse);

		// 关闭连接
		client.close();
	}*/

	// 高亮查询结果 处理 搜索
	/* @Test
	public void demo9() throws IOException {
		// 创建连接搜索服务器对象
		Client client = TransportClient
				.builder()
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("127.0.0.1"), 9300));

		ObjectMapper objectMapper = new ObjectMapper();

		// 搜索数据
		SearchRequestBuilder searchRequestBuilder = client
				.prepareSearch("blog2").setTypes("article")
				.setQuery(QueryBuilders.termQuery("content", "我们"));

		// 高亮定义
		searchRequestBuilder.addHighlightedField("content"); // 对content字段进行高亮
		searchRequestBuilder.setHighlighterPreTags("<em>"); // 前置元素
		searchRequestBuilder.setHighlighterPostTags("</em>");// 后置元素

		SearchResponse searchResponse = searchRequestBuilder.get();

		SearchHits hits = searchResponse.getHits(); // 获取命中次数，查询结果有多少对象
		System.out.println("查询结果有：" + hits.getTotalHits() + "条");
		Iterator<SearchHit> iterator = hits.iterator();
		
		int i=0;
		while (iterator.hasNext()) {
			if(i>0) return;
			i++;
			
			SearchHit searchHit = iterator.next(); // 每个查询对象

			// 将高亮处理后内容，替换原有内容 （原有内容，可能会出现显示不全 ）
			Map<String, HighlightField> highlightFields = searchHit
					.getHighlightFields();
//			HighlightField titleField = highlightFields.get("title");
			HighlightField contentField = highlightFields.get("content");

			// 获取到原有内容中 每个高亮显示 集中位置 fragment 就是高亮片段
//			Text[] fragments = titleField.fragments();
//			String title = "";
//			for (Text text : fragments) {
//				title += text;
//				System.out.println("片段里的内容="+title);
//			}
			
			Text[] contentFragments = contentField.fragments();
			String content = "";
			int j=0;
			for (Text text : contentFragments) {
				System.out.println("i="+i + " j="+j++ +"片段里的内容text="+text+"\n");
				content += text;
			}
			
			System.out.println(i+"片段里的内容content="+content);
			
			// 将查询结果转换为对象
			Article article = objectMapper.readValue(
					searchHit.getSourceAsString(), Article.class);

			// 用高亮后内容，替换原有内容
//			article.setTitle(title);
			article.setContent(content);
			
//			System.out.println(article);
		}

		// 关闭连接
		client.close();
	}*/
}
