package com.etoak.crawl.main;

import com.etoak.crawl.link.Links;
import com.etoak.crawl.page.Page;
import com.etoak.crawl.page.PageParserTool;
import com.etoak.crawl.page.RequestAndResponseTool;
import com.etoak.crawl.page.TeamInfo;
import com.etoak.crawl.util.FileTool;

import java.util.ArrayList;

public class MyCrawler {

	/**
	 * 使用种子初始化 URL 队列
	 *
	 * @param seeds
	 *            种子 URL
	 * @return
	 */
	private void initCrawlerWithSeeds(String[] seeds) {
		for (int i = 0; i < seeds.length; i++) {
			Links.addUnvisitedUrlQueue(seeds[i]);
		}
	}

	/**
	 * 抓取过程
	 *
	 * @param seeds
	 * @return
	 * @throws Exception 
	 */
	public void crawling(String[] seeds) throws Exception {

		// 初始化 URL 队列
		initCrawlerWithSeeds(seeds);

		// 循环条件：待抓取的链接不空且抓取的网页不多于 1000
		while (!Links.unVisitedUrlQueueIsEmpty() && Links.getVisitedUrlNum() <= 1000) {

			// 先从待访问的序列中取出第一个；
			String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
			if (visitUrl == null) {
				continue;
			}

			// 根据URL得到page;
			Page page = RequestAndResponseTool.sendRequstAndGetResponse(visitUrl);

			// 将已经访问过的链接放入已访问的链接中；
			Links.addVisitedUrlSet(visitUrl);

			// 得到超链接
			ArrayList<String> links = PageParserTool.getAttrs(page, "a", "href");
			for (String link : links) {
				if (link.contains("history_data")) {
					link = "http://info.sporttery.cn/football/history/" + link;
					Links.addUnvisitedUrlQueue(link);
					System.out.println("新增爬取路径: " + link);
				} else if (link.contains("fb_team_info")) {
					TeamInfo info = RequestAndResponseTool.getTeamImg(link);
					if (info != null) {
						System.out.println(info);
						FileTool.saveToLocal(info);
					}
				}
			}
		}
	}

	// main 方法入口
	public static void main(String[] args) throws Exception {
		MyCrawler crawler = new MyCrawler();
		crawler.crawling(new String[] { "http://info.sporttery.cn/football/history/data_center.php" });
	}
}
