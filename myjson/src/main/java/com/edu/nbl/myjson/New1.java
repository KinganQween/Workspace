package com.edu.nbl.myjson;

import java.util.List;

/**
 * Created by Administrator on 17-9-19.
 */

public class New1 {

    /**
     * message : OK
     * status : 0
     * data : [{"summary":"\t\t\t\t\t\t\t\t\t对于弱小的菲海军来说，这艘万吨级巨舰的加入犹如强心针，将大大提高菲海军的战略投送能力。在南海多国博弈加剧的背景下，战略投送能力对菲守住南海攫取的利益无疑是个有力筹码。方法\r\n\t\t\t\t\t\t\t\t","icon":"http://118.244.212.82:9092/Images/20160517115649.jpg","stamp":"2016-05-17 08:17:15.0","title":"菲万吨级战略运输舰亮相 为争夺南海添筹码","nid":26,"link":"http://war.163.com/16/0517/08/BN8LI9TE00014OMD.html","type":1},{"summary":"\u201c杜特尔特不能忘了，继续与日本和美国以及其他相关国家合作，是地区稳定的关键\u201d。美国战略与国际研究中心研究员葛莱仪16日对菲律宾《星报》表示，杜特尔特将会发现，处理对华和对美关系是个大挑战，在两者之间搞平衡很难。","icon":"http://118.244.212.82:9092/Images/20160517115206.jpg","stamp":"2016-05-17 08:01:00.0","title":"中菲关系看到\"改善机会\" 南海博弈仍在进行","nid":21,"link":"http://war.163.com/16/0517/08/BN8KP8GN00014OMD.html","type":1},{"summary":"5月16日上午，解放军陆军司令员李作成在八一大楼与来访的巴基斯坦陆军参谋长拉希尔举行了会谈。这是李作成担任陆军司令员以来，首次接待外国陆军领导人，也是中国首任陆军司令员在军事外交中的\u201c首秀\u201d。","icon":"http://118.244.212.82:9092/Images/20160517115254.jpg","stamp":"2016-05-17 07:53:00.0","title":"中国解放军陆军司令外事\"首秀\"给了巴基斯坦","nid":22,"link":"http://war.163.com/16/0517/07/BN8KCQIO00014OMD.html","type":1},{"summary":"正在贵州省平塘县建设的世界最大单口径射电望远镜\u2014\u2014500米口径球面射电望远镜（FAST），已完成94％以上的面板安装，整个工程进入收尾阶段。","icon":"http://118.244.212.82:9092/Images/20160517115331.jpg","stamp":"2016-05-11 12:00:00.0","title":"国产世界最大单口径射电望远镜进入收尾阶段","nid":23,"link":"http://war.163.com/16/0511/12/BMPK3HEO00014OMD.html","type":1},{"summary":"据美国《国家利益》双月刊网站5月9日报道称，中国和印度都雄心勃勃地想要成为亚洲\u201c大国\u201d。两国分别有着亚洲规模最大和第二大的军队，国防预算也分别名列亚洲第一和第二位。","icon":"http://118.244.212.82:9092/Images/20160517115414.jpg","stamp":"2016-05-11 11:17:36.0","title":"美媒:中国军工水平远超印度 国产武器更先进","nid":24,"link":"http://war.163.com/16/0511/11/BMPHG4F900014OMD.html","type":1},{"summary":"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t4月18日，海蝶音乐创始人许环良正式宣布自己辞去CEO的职务，离开了亲手创办的唱片公司，就在国内资本蠢蠢欲动、外资巨鳄觊觎内地市场的时节，唱片公司老板转身的原因是什么？www\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t","icon":"http://118.244.212.82:9092/Images/20140509075810c679d.png","stamp":"2014-08-20 10:39:01.0","title":"音乐生态乱象 是互联网的错么？","nid":6,"link":"http://tech.163.com/14/0509/06/9RPKCLTM00094ODU.html","type":1},{"summary":"谈到诺基亚手机，人们印象最深的就是质量好。据微软高管爆料，诺基亚一部手机日前在阿富汗再次挡住子弹，救人一命。 \t\t\t\r\n\t\t\t\t\t\t\t\t","icon":"http://118.244.212.82:9092/Images/20161009023601.jpg","stamp":"2013-04-03 14:31:20.0","title":"诺基亚手机续写\u201c防弹\u201d传奇 阿富汗男子捡回一条命","nid":31,"link":"http://mini.eastday.com/a/161009135622768.html","type":1}]
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "New1{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * summary : 									对于弱小的菲海军来说，这艘万吨级巨舰的加入犹如强心针，将大大提高菲海军的战略投送能力。在南海多国博弈加剧的背景下，战略投送能力对菲守住南海攫取的利益无疑是个有力筹码。方法

         * icon : http://118.244.212.82:9092/Images/20160517115649.jpg
         * stamp : 2016-05-17 08:17:15.0
         * title : 菲万吨级战略运输舰亮相 为争夺南海添筹码
         * nid : 26
         * link : http://war.163.com/16/0517/08/BN8LI9TE00014OMD.html
         * type : 1
         */

        private String summary;
        private String icon;
        private String stamp;
        private String title;
        private int nid;
        private String link;
        private int type;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getStamp() {
            return stamp;
        }

        public void setStamp(String stamp) {
            this.stamp = stamp;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
