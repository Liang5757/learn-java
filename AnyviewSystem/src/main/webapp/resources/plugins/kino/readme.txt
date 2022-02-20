kino.razor:an easy to use,razor style javascript template tool

github: https://github.com/kinogam/kino.razor


How to use

use on a webpage

    <script type="text/javascript" src="kino.razor.js"></script>
    //or minjs
    <script type="text/javascript" src="kino.razor.min.js"></script>
install in node.js

    npm install razor
require js

var razor = require("razor");
you can pass two parameters to kino.razor then get a converted string:

    var str = kino.razor("Hey, @name!", { name: 'kino' });
    //result: "Hey, kino!"
pass one parameter to kino.razor() should return a template function

    var r = kino.razor("hello!@name");
    //type of r is function
and automatic handle missing parameters

    var templateStr = "Hey, @xxx";
    var str = kino.razor(templateStr, {});
    //result: "Hey, "
given a template function to kino.razor() then it should return a convented string(recommond,and get good performance)

    var tf = kino.razor("hello!@name");
    var str = kino.razor(tf, { name: 'kino' });
    //result: "hello!kino"
set javascript code block

    var templateStr = "@{var fname='kino';}this is @fname @lname";
    var str = kino.razor(templateStr, {lname:'jack'});
    //result: "this is kino jack"
use condition syntax

    var templateStr = "@if(1==0){<span>if you see this word,your test is failed</span>}";
    var str = kino.razor(templateStr, {});
    //result: ""
loop, like while and for

    var templateStr = "@for(var i = 0; i < 3; i++){<span>@i</span>}";
    var str = kino.razor(templateStr, {});
    //result: "<span>0</span><span>1</span><span>2</span>"

    templateStr = "@{var i = 3;}@while(i--){<span>@i</span>}";
    str = kino.razor(templateStr, {});
    //result: "<span>2</span><span>1</span><span>0</span>"
use @Html.escape for escape string to html

    var templateStr = "<input yyy='@Html.escape(test)' xxx=\"@Html.escape(otherAttr)\" />";
    var str = kino.razor(templateStr, { test: "kino's test", otherAttr: "\"one more test\"" });
    //result: "<input yyy='kino&#x27;s test' xxx=\"&quot;one more test&quot;\" />"
@@ and @} will be escaped as @ and }

    var templateStr = "{@name@@gmail.com@}";
    var str = kino.razor(templateStr, { name: 'kino' });
    //result: '{kino@gmail.com}'
use custom symbol instead of '@'

    kino.razor.use("&");
    var str = kino.razor("&name@&email", { name: "kinogam", email: "gmail.com" });
    //"kinogam@gmail.com"

    kino.razor.use("$");
    var str = kino.razor("$name@$email", { name: "kinogam", email: "gmail.com" });
    //"kinogam@gmail.com"
support style @(name):

    var str = kino.razor("Hey, zz@(name)zz!", { name: 'kino' });
    //result: "Hey, zzkinozz!"