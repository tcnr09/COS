<?php

	//允许区域网路访问
	header("Access-Control-Allow-Origin:*");

	//Food Open Data url
	$url ="http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvTravelFood.aspx";
	/*
	*	file_get_contents() 把整个文件读入一个字符串中
	*	语法:
	*	file_get_contents(path,[include_path],[context,start],[max_length]) 
	*
	*	参数 							描述
	*	path 				必需。规定要读取的文件。
	*	include_path 		可选。如果您还想在 include_path（在 php.ini 中）中搜索文件的话，请设置该参数为 '1'。
	*	context 			可选。规定文件句柄的环境。context 是一套可以修改流的行为的选项。若使用 NULL，则忽略。
	*	start 				可选。规定在文件中开始读取的位置。该参数是 PHP 5.1 中新增的。
	*	max_length 			可选。规定读取的字节数。该参数是 PHP 5.1 中新增的。
	*
	*/
	$content = file_get_contents($url);

	//output
	echo $content;


?>