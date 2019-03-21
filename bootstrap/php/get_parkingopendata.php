<?php
	//允许区域网路访问
	header("Access-Control-Allow-Origin:*");
	
	//Parking Open Data url
	
	//$url = "https://data.tycg.gov.tw/opendata/datalist/datasetMeta/download?id=f4cc0b12-86ac-40f9-8745-885bddc18f79&rid=0daad6e6-0632-44f5-bd25-5e1de1e9146f";
	
	//解决failed to open stream: HTTP request failed!
	//无法使用HTTPS
	$url = "http://data.tycg.gov.tw/opendata/datalist/datasetMeta/download?id=f4cc0b12-86ac-40f9-8745-885bddc18f79&rid=0daad6e6-0632-44f5-bd25-5e1de1e9146f";

	/***************************************************
	*	file_get_contents() 把整个文件读入一个字符串中
	*	语法:
	*	file_get_contents(path,[include_path],[context,start],[max_length]) 
	*
	*	参数 							描述
	*	path 				必需。规定要读取的文件。
	*	include_path 		可选。如果您还想在 include_path（在 php.ini 中）中搜索文件的话，请设置该参数为 '1'。
	*	context 			可选。规定文件句柄的环境。context 是一套可以修改流的行为的选项。若使用 NULL，则忽略。
	*	start 				可选。规定在文件中开始读取的位置。PHP 5.1 中新增的。
	*	max_length 			可选。规定读取的字节数。PHP 5.1 中新增的。
	*
	*
	******************************************************/
	
	$content=file_get_contents($url);	
	
	echo ($content);

?>