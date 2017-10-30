package shortestPaths.Main;
/*
 *
 * Copyright (c) 2004-2008 Arizona State University.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY ARIZONA STATE UNIVERSITY ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL ARIZONA STATE UNIVERSITY
 * NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

import Method.Parameter;
import shortestPaths.graph.Graph;
import shortestPaths.graph.VariableGraph;
import shortestPaths.shortestpaths.YenTopK;

/**
 * TODO Need to redo!
 * @author <a href='mailto:Yan.Qi@asu.edu'>Yan Qi</a>
 * @version $Revision: 784 $
 * @latest $Id: YenTopKTest.java 46 2010-06-05 07:54:27Z yan.qi.asu $
 */

public class YenTopKTest
{
	public static void main(String args[])
	{
		//数据图结点个数;
		Parameter.NumOfPatternNode = 83000;
		Graph graph = new VariableGraph("D:\\研究生\\模式识别\\旧实验\\测试数据" +
				"\\data\\CitHepTh\\25\\CitHepTh.txt");

		//开始查找top-k个最短路径;
		YenTopK yenAlg = new YenTopK(graph, graph.getVertex(7779), graph.getVertex(5424));
		int i=0;
		while(yenAlg.hasNext())
		{
			System.out.println("Path "+i+++" : "+yenAlg.next());
		}
		
		System.out.println("Result # :"+i);
		System.out.println("Candidate # :"+yenAlg.getCadidateSize());
		System.out.println("All generated : "+yenAlg.getGeneratedPathSize());
	}
}
