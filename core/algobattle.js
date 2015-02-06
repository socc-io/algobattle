var fs = require("fs");
var TEMPLATE_PATH = "./core/template/mookzzibba";
var MAIN_SRC_FILE = TEMPLATE_PATH + "/mookzzibba_main.c";
var Aplayer = process.argv[2];
var Bplayer = process.argv[3];

console.log("["+Aplayer+"] vs [" + Bplayer + "]");
var OUTPUT_SRC_FILE = "./core/generate/"+Aplayer + "_" + Bplayer + ".c";

var main_src = fs.readFileSync(MAIN_SRC_FILE,"utf-8");
var a_src = fs.readFileSync("./source/"+Aplayer+".c","utf-8");
var b_src = fs.readFileSync("./source/"+Bplayer+".c","utf-8");

var name_template_pre = 'if (name != NULL) sprintf(name, "';
var name_template_post = '");\n';

main_list = main_src.split("//*************");
a_src_list = a_src.split("//*************");
b_src_list = b_src.split("//*************");

main_list[1] += a_src_list[1];
main_list[1] += b_src_list[1];

main_list[3] += name_template_pre +Aplayer+ name_template_post + a_src_list[3];
main_list[5] += name_template_pre +Aplayer+ name_template_post + b_src_list[3];

var output_src = "";
for (i in main_list){
	output_src += main_list[i];
}

fs.writeFileSync(OUTPUT_SRC_FILE,output_src);

var spawn = require('child_process').spawn;
//var child = spawn('battle.out', [OUTPUT_SRC_FILE]);
