package com.pakachu.apaydinfitness.helpers;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.pakachu.apaydinfitness.R;

public class CheckDetay {
    Activity activity;
    public String hakkinda = "Açıklama Yok";
    public String videoURL = "NO URL";
    public int gifID = 0;
    public Drawable musclesWorked = null;

    public CheckDetay(Activity activity, String hareketAdi) {
        this.activity = activity;
        CheckNow(hareketAdi);
    }

    private void CheckNow(String hareketAdi) {
        ImageProcess imageProcess = new ImageProcess(activity);
        switch (hareketAdi) {
            case "isinma":
                hakkinda = "Isınma hareketleri sayesinde bedeninizdeki kan dolaşımı artar böylece kas, ligament ve tendon gibi tüm dokular artan dolaşım ile egzersize hazırlanır. Isınma sadece bedeni değil aynı zamanda zihni de egzersize hazırlar. Beden, zihin ve sinir sisteminin hazırlanması ile eklemler spora uygun hale gelir.";
                videoURL = "https://youtu.be/vtDrQJ6zCiE";
                gifID = R.drawable.isinma;
//                musclesWorked= imageProcess.GetDrawableFromAssets("musclesworked/isinma.jpg",false);
                break;
            case "kardiyo":
                hakkinda = "Hücreler, ihtiyaçları olan oksijeni ve besin maddelerini, dolaşım sistemi içinde bulunan kandan sağlamaktadır. Aynı zamanda da metabolizmaları sonucu ortaya çıkan karbondioksidi ve atık maddelerini atılım organlarına yönlendirmesi için kana vermektedirler. Kanın, tüm bu görevleri yerine getirmesini sağlayan sisteme ise dolaşım sistemi (kardiyovasküler sistem) denir. Kardiyo egzersizleri, kardiyovasküler sistemin güçlendirilmesine yardımcı olabilecek çeşitli egzersizleri ifade etmektedir. Bisiklet, ip atlama, yüzme ve koşu kardiyo egzersizleri arasında yer alır. Bu tür egzersizler, nefes alıp verme sıklığının artmasına yol açmaktadır. Bu da kişinin kalp atışında hızlanmaya ve buna bağlı olarak da hücrelerin daha fazla yağ yakmasını sağlamaktadır. Kardiyo, kalp ve akciğeri yoğun bir tempoda çalıştırarak, kişinin nefes alışverişini daha düzenli hale getirmektedir. Aynı zamanda da kalbin daha sağlıklı kan pompalamasına olumlu etkide bulunmaktadır.";
                videoURL = "https://youtu.be/-kFIwK71AFw";
                gifID = R.drawable.kardiyo;
//                musclesWorked= imageProcess.GetDrawableFromAssets("musclesworked/isinma.jpg",false);
                break;
            case "off":
                hakkinda = "Spor esnasında çalıştırılan kas, tendon ve eklemlerde gözle görülemeyecek kadar küçük travmalar meydana gelebilir. Özellikle kaslarda meydana gelen mikro yırtılmalar aslında daha güçlü ve büyük kas gruplarının oluşabilmesi için yaşanır. Dinlenme günü vererek vücudun bu mikro yaralanmaları iyileştirmesine zaman vermek daha güçlü bir bedene sahip olmanın ilk adımı olur. Mikro travmaların iyileşmesine izin verilmezse sürekli olarak fiziksel strese maruz bırakılan bedende spor esnasında yaralanma veya sürekli devam eden ağrı formunda kalıcı hasarlar yaşanabilir. Kemiklerin kendini yeniden şekillendirme süreci dahilinde spor sonucu yaşanan mikro çatlaklara iyileşme alanı tanınmazsa ciddi burkulmalara hatta kırılmalara yol açabilir. Dışarıdan ekstra bir fiziksel strese maruz kalmayan vücut kendi içinde dengelenme sürecine girer. Özellikle yeni bir egzersiz tipi veya spor günlük rutine dahil edilmeye başlanmışsa vücuda adapte olması için süre tanımak gerekir. Daha iyi bir kondisyona sahip olmak vücudun yapılan egzersizlere göre kas dokusunu, kemik yoğunluğunu ve kardiyovasküler hareketi uyarlamasından geçer. Yapılan egzersiz rutininin sürdürülebilir olması sporun bir zorunluluk veya bir ödev olarak görülmemesine dayanır. Dinlenme günleri vermek bedeni rahatlatırken “spor yapma gerekliliğinin” zihinde yaratabileceği baskının azalmasını, duygusal olarak yaşanabilecek tükenmişlik, bıkkınlık hislerinin engellenmesini ve sporu tamamen terk etme hissinin önüne geçilmesini sağlar.";
//                videoURL = "https://youtu.be/-kFIwK71AFw";
                gifID = R.drawable.off_day;
//                musclesWorked= imageProcess.GetDrawableFromAssets("musclesworked/isinma.jpg",false);
                break;

            //GÖĞÜS HAREKETLERİ
            case "bench press":
                hakkinda = "Bench press Pectoralis majör – Göğüs kaslarını geliştirmeye yarayan temel egzersizdir. Üst vücut bölgesindeki birçok kasın gelişimin de fayda sağlamaktadır. Örneğin on omuz kasları, arka kol kasları, karın kasları, bacak kaslarının da aktivasyonu sağlamaktadır. Konsantre ve dikkatli bir şekilde hareketi yaptığımızda bu kaslarda çalışır.";
                videoURL = "https://youtu.be/AIjsngj5xck";
                gifID = R.drawable.bench_press;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/benchpress.jpg", false);
                break;
            case "incline dumbell press":
                hakkinda = "İncline dumbbell press hareketi, dik eğimli bench sehpası üzerinde, göğüs hizasında bulunan dumbbell’ları yere dik olarak iterek yapılan üst göğüs egzersizidir. Incline dumbbell bench press hareketi, çok etkili bir üst göğüs hareketidir. Bench press ile yapılış bakımından çok benzer tek farkı ise, sehpa açısının farklı olmasıdır. Bu farklılık ise, kaldırdığınız ağırlığı da düşürür.";
                videoURL = "https://youtu.be/rKC9xLCRJ64";
                gifID = R.drawable.incline_dumbell_press;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/inclinedumbellpress.jpg", false);
                break;
            case "chest press":
                hakkinda = "Chest press machine hareketi, göğüs kaslarını güçlendirmeye yardımcı olan bir egzersiz olmaktadır. Bunun yanında omuz bölgesindeki ve üst kol bölgesindeki kaslarında gelişiminde etkin rol almaktadır. Chest press machine hareketi sırasında vücudun en çok çalışan bölümü omuz ve kollar olmaktadır Bu yüzden bu bölgedeki kasların gelişmesine doğrudan etki etmektedir.";
                videoURL = "https://youtu.be/xUm0BiZCWlQ";
                gifID = R.drawable.chest_press;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/chestpress.jpg", false);
                break;
            case "cable crossover":
                hakkinda = "Cable Crossover, en çok göğüs kaslarını çalıştırır. Göğsün ortasından başlayarak Clavicle köprücük kemiğinin iç kısmı ve altıncı göğüs kafesi kemiğine kadar ulaşır. Aynı zamanda humerus üst kol kemiğine de bağlanır. Ön omuz kasını da çalıştırmaya yardımcı olur. Clavicle köprücük kemiğinin dış tarafından başlar ve Humerus üst kol kemiğine doğru bağlanır. Üst kol kasıdır. Kolun iç kısmında yer alan kısa kol kasıdır. Scapula omuz kemiğinden başlayarak ve Radius ön kol kemiğinin üst kısmına doğru bağlanır.";
                videoURL = "https://youtu.be/taI4XduLpTk";
                gifID = R.drawable.cable_cross_over;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/cablecrossover.jpg", false);
                break;
            case "machine fly":
                hakkinda = "Machine fly hareketi güzel bir sıkıştırma egzersizidir. Alt göğüsün gelişmesi için Lover major kas grubunun çalışması gerekir. Machine Fly ile alt göğüs kaslarını geliştirebilirsiniz. Derinlemesine bir göğüs arası istiyorsanız uygulamanız gereken egzersizler arasındadır. Üst Göğüs kaslarının gelişmesi için, göğüs hareketleri ile Up Pectoralis major kas gurubunun çalışması gerekir. Göğüs geliştirme hareketlerinden, Machine fly yaparak üst göğüs kaslarınızın gelişmesini sağlamış olursunuz. Ön Kol Kasları (Biceps): ön kol kası olan biceps brachii, pazularımızın gelişmesi için her ne kadar biceps hareketleri yapmamız gereksede Machine fly, doğru yapılması halinde genel olarak ön kol kaslarını çalıştıran hareketler arasında yer almaktadır. Serratus anterior, Boksör kası olarak bilinen serratus anterior kası, oldukça zor gelişen bir kas grubudur. Tam göğüs altı ile mide kasları arasında yer alan serratus antreior kası,  Machine fly hareketi ile çalışma sağlar.";
                videoURL = "https://youtu.be/Z57CtFmRMxA";
                gifID = R.drawable.machine_fly;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/machinefly.jpg", false);
                break;
            case "dumbell fly":
                hakkinda = "Göğüsleri sıkıştırmaya ve ortadaki çizgiyi belirginleştirmeye yarayan hareket. dumbbells ile gerceklestirilen ve gogus kasini calistirmaya yarayan bir fitness hareketidir. ana göğüs egzersizi olmayıp yardımcı ve izole bir harekettir.";
                videoURL = "https://youtu.be/MxfVDZrsdZ0";
                gifID = R.drawable.dumbell_fly;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/dumbellfly.jpg", false);
                break;
            case "incline bench press":
                hakkinda = "İncline dumbbell press hareketi, dik eğimli bench sehpası üzerinde, göğüs hizasında bulunan dumbbell’ları yere dik olarak iterek yapılan üst göğüs egzersizidir. Incline dumbbell bench press hareketi, çok etkili bir üst göğüs hareketidir. Bench press ile yapılış bakımından çok benzer tek farkı ise, sehpa açısının farklı olmasıdır. Bu farklılık ise, kaldırdığınız ağırlığı da düşürür.";
                videoURL = "https://youtu.be/DutO2wEtqaw";
                gifID = R.drawable.incline_bench_press;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/inclinebenchpress.jpg", false);
                break;
            case "decline bench press":
                hakkinda = "Decline bench press, ters eğimli olan bir sehpa üzerinde barbell'i göğüs hizasına gelecek şekilde ve yere dik olacak şekilde hareketi sağlanarak yapılan bir alt göğüs egzersizi olarak tanımlanabilir.";
                videoURL = "https://youtu.be/OR6WM5Z2Hqs";
                gifID = R.drawable.decline_bench_press;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/declinebenchpress.jpg", false);
                break;

            //ÖN KOL HAREKETLERİ
            case "bicep curl":
                hakkinda = "Bu hareket en temel hareketlerden bir tanesidir. Dumbell biceps curl hareketini doğru yapmak, birçok hareketi kolay bir şekilde yapmayı da beraberinde getirir. Bununla birlikte vücutta güzel bir görünüm sağlar. Kollar daha iri ve hacimli görünür. Bu kas grubu sırt çalışmak için oldukça önemli bir kas grubudur.";
                videoURL = "https://youtu.be/ykJmrZ5v0Oo";
                gifID = R.drawable.bicep_curl;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/bicepcurl.jpg", false);
                break;
            case "hammer curl":
                hakkinda = "Dumbbell hammer curl, fitness yapan kişiler tarafından sıkça tercih edilen bir ön kol egzersizidir. Bu Egzersiz dumbbell kullanarak, avuç içleri birbirine bakar halde tutularak yapılan bir harekettir. Egzersiz esnasında dirsekler sabit tutularak ön kol kaslarının çalışması ve gelişmesi amaçlanır.";
                videoURL = "https://youtu.be/WGTCVgCqLqM";
                gifID = R.drawable.hammer_curl;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/hammercurl.jpg", false);
                break;
            case "preacher curl":
                hakkinda = "Kendine özel sehpasında çeşitli araçlar kullanılarak yapılan bu hareket açık ve kapalı tutuşlarla, çeşitli kasların ayrı ayrı çalışmasını sağlayan bir pazu geliştirme egzersizi olarak ifade edilmektedir.";
                videoURL = "https://youtu.be/fIWP-FRFNU0";
                gifID = R.drawable.preacher_curl;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/preachercurl.jpg", false);
                break;
            case "cable curl":
                hakkinda = "Cable Curl biceps hareketine odaklı bir harekettir. İstasyon makinelerinde bu hareket yapılabilir. Makara yardımıyla aşağıdan yukarı doğru çekilerek yapılan bir ön kol egzersizdir. Serbest ağırlığa göre daha basit olarak gelebilir bunun sebebi ise kamara sistemidir.";
                videoURL = "https://youtu.be/85kXYq7Ssh4";
                gifID = R.drawable.cable_curl;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/cablecurl.jpg", false);
                break;

            //SIRT HAREKETLERİ
            case "barfiks":
                hakkinda = "Barfiks, yüksek bir noktaya asılan bardan tutularak başın barın üzerine kaldırılma hareketidir. Barfiks, genel anlamda zor bir hareket olarak kabul edilir. Özellikle kol, sırt ve omuzların çalışmasını sağlar. Bu nedenle güç-kuvvet çalışmalarında getirisi çok yüksektir. Barfiks hareketi, insan vücudundaki kol kaslarının ne kadar güçlü olduğunu gösterir. Bu nedenle sporla ilgili meslek ve aktivitelerin hemen hepsinin programında barfiks yer almaktadır. Ayrıca polis, asker ya da güvenlik görevlisi gibi meslek gruplarının sınavlarında, adayların barfiks başarısı ayrı bir öneme sahiptir.";
                videoURL = "https://youtu.be/Gq2oQx26emc";
                gifID = R.drawable.barfiks;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/barfiks.jpg", false);
                break;
            case "machine row":
                hakkinda = "Vücudun en büyük kas gruplarından biri olan sırt kasları, sağlık ve estetik açıdan oldukça önemlidir. Şekilli ve güçlü sırt kasları hem giydiğiniz kıyafetlerin üzerinizde daha iyi görünmesini sağlar hem de duruş ve oturuş bozukluklarının giderilmesine yardımcı olur.";
                videoURL = "https://youtu.be/w49UGytisik";
                gifID = R.drawable.machine_row;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/machinerow.jpg", false);
                break;
            case "lat pull":
                hakkinda = "Vücudun en büyük kas gruplarından biri olan sırt kasları, sağlık ve estetik açıdan oldukça önemlidir. Şekilli ve güçlü sırt kasları hem giydiğiniz kıyafetlerin üzerinizde daha iyi görünmesini sağlar hem de duruş ve oturuş bozukluklarının giderilmesine yardımcı olur.";
                videoURL = "https://youtu.be/Z_3xHwuO8Tk";
                gifID = R.drawable.lat_pull;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/latpull.jpg", false);
                break;
            case "seated cable row":
                hakkinda = "Vücudun en büyük kas gruplarından biri olan sırt kasları, sağlık ve estetik açıdan oldukça önemlidir. Şekilli ve güçlü sırt kasları hem giydiğiniz kıyafetlerin üzerinizde daha iyi görünmesini sağlar hem de duruş ve oturuş bozukluklarının giderilmesine yardımcı olur.";
                videoURL = "https://youtu.be/GZbfZ033f74";
                gifID = R.drawable.seated_cable_row;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/seatedcablerow.jpg", false);
                break;
            case "hyperextension":
                hakkinda = "Hyperextension ters mekik olarak da adlandırılabilen bir egzersizdir. Klasik mekik hareketini andırması sebebiyle bu adı almıştır. Mekik ile aynı mantığa sahip olan bu egzersiz, yüzüstü yatarak yapılır. Öncelikli amaç ise sırt kaslarını ekipmansız olarak geliştirebilmektir. Hızlı ya da yavaş tekrarlara müsait olan Hyperextension hareketi, bel omurlarının sağlığını korumak ve vücudun duruş pozisyonunu düzeltmek açısından da önemli faydalara sahiptir.";
                videoURL = "https://youtu.be/ph3pddpKzzw";
                gifID = R.drawable.hyper_extension;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/hyperextension.jpg", false);
                break;
            case "dumbell row":
                hakkinda = "Dumbbell row hareketi; esas olarak düz bir benchin üstüne bir dizin ve bir elin konularak üst vücudun yere paralel şekle getirip, tek kol aracılığı ile dumbbell'ı aşağıdan yukarı doğru çekmek sureti ile yapılmış olunan trapez, kanat ve sırt egzersizini ifade etmektedir.";
                videoURL = "https://youtu.be/roCP6wCXPqo";
                gifID = R.drawable.dumbell_row;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/dumbellrow.jpg", false);
                break;
            case "barbell row":
                hakkinda = "Barbell row, diğer adı ile Bent Row adı verilen egzersiz; Diz seviyesindeki Barbell’i, karın bölgesine kadar çekmek sureti ile yapılabilen kanat ve orta sırt egzersizine verilen bir isimdir. Duruş formu biraz sıkıntılı olsa bile özellikle ilk denemelerde boş bir bar kullanarak rahat bir şekilde uyum sağlamak mümkündür. Bu hareket en az dumbbell row hareketi kadar etkili olmaktadır. Barbell row hareketinin avantajıysa her iki kanat kasını aynı anda çalıştırmasıdır ve çok daha yüksek kiloları kaldırabilme imkanı sunmasıdır. Barbell row hareketinin bitiminde önceden hiç hissedilmeyen bir yanma ve aynı zamanda bir gelişme hissedilmektedir. Bu da hareketin ne kadar etkili olduğunu göstermektedir.";
                videoURL = "https://youtu.be/aFzHLDEK-tg";
                gifID = R.drawable.barbell_row;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/barbellrow.jpg", false);
                break;
            case "rope pulldown":
                hakkinda = "Hareket üzerinde halat olan ve üstte  makarası olan her makinede yapılır.Özel yapılmış ve kalın halatlı aparatın iki ucundan tutulur ve vücut biraz eğiktir.Nefes vererek halat karın boşluğuna doğru iyice çekilir ve nefes alarak yavaşça başlangıç noktasına dönülür.";
                videoURL = "https://youtu.be/7neTy_9zbjg";
                gifID = R.drawable.rope_pull_down;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/ropepulldown.jpg", false);
                break;

            //ARKA KOL HAREKETLERİ
            case "dumbell skullcrusher":
                hakkinda = "Ez-bar, dumbell ya da barbell yardımıyla, düz bench sehpası üzerinde, ağırlığı baş bölgesinin üstünden, göğüs bölgesine kaldırmak suretiyle yapılan, triceps brachii – arka kol kaslarını çalıştıran bir fitness hareketidir.";
                videoURL = "https://youtu.be/ir5PsbniVSc";
                gifID = R.drawable.dumbell_skull_crusher;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/dumbellskullcrusher.jpg", false);
                break;
            case "close grip bench press":
                hakkinda = "Close grip bench press hareketi, bench press hareketine oldukça benzese de bir çeşit arka kol ARKA egzersizidir. Arka kol egzersizlerinizi yaparken bu hareketi tercih ederseniz hem Anterior deltoid (ön omuz) hem de Triceps Brachii (arka kol) kaslarınızı da çalıştırırsınız.";
                videoURL = "https://youtu.be/wxVRe9pmJdk";
                gifID = R.drawable.close_grip_bench_press;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/closegripbenchpress.jpg", false);
                break;
            case "rope pushdown":
                hakkinda = "Triceps pushdown hareketi; İstasyonda bar veya halat kullanılarak, ağırlığı yukarıdan aşağı çekmek suretiyle yapılan bir arka kol egzersizidir. Gelişim açısından harikalar yaratır. Tüm arka kol hareketlerinin arasından sıyrılıp ilk üçe girebileceğini rahatlıkla söyleyebiliriz.";
                videoURL = "https://youtu.be/vB5OHsJ3EME";
                gifID = R.drawable.rope_push_down;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/ropepushdown.jpg", false);
                break;
            case "cable pushdown":
                hakkinda = "Triceps pushdown hareketi; İstasyonda bar kullanılarak, ağırlığı yukarıdan aşağı çekmek suretiyle yapılan bir arka kol egzersizidir. Gelişim açısından harikalar yaratır.";
                videoURL = "https://youtu.be/2-LAMcpzODU";
                gifID = R.drawable.cable_push_down;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/ropepushdown.jpg", false);
                break;
            case "tricep dumbell extension":
                hakkinda = "Şimdi bu egzersizle her zaman ayaklarınızla başlayın. İyi bir temel alın; Bunun için iyi bir dengeye ihtiyacınız var, çünkü tepenize çıkacaksınız. Şimdi, burada, bu dambıl ile başınızın arkasından başlayacaksınız. Kendine vurmamaya dikkat et. Ve önemli olan, o kası gerçekten germek, yukarı çıkmak, kasılmak ve sıkmak istemenizdir. Şimdi bunu ayakta yapmak iyidir, çünkü aynı zamanda çekirdeğinizi de çalıştırır, bu yüzden gerçekten iyi bir dengeye sahip olmanız gerekir. Yine, tamamen aşağı, gerin, sıkın. Bu egzersizde nefes almak da çok önemlidir, yerçekimine karşı çalışırken nefes vermek istersiniz - yani ben yukarı çıkarken yer çekimine karşı çalışıyorum. Vay canına. Triceps'inizi sıkın. Bırakın, geri gelin, nefes alın. Şimdi görüyorsunuz, erkekler çok ağır vurduklarında ve ağırlığı atmaya çalıştıklarında ya da dengesiz olduklarında bu egzersizde kötü alışkanlıklar oluyor, sadece garip görünüyor. Yine rahat edeceğiniz bir ağırlık seçmek istiyorsunuz ve 10 ila 15 tekrar yapabilirsiniz. ile birlikte. Kas büyümesi, kas boyutu anlamına gelen hipertrofinin amacı budur. Şimdi bu kolla 10 ila 15 tekrarınız bittiğinde, diğer kola geçersiniz. Yine güçlü taban, güçlü çekirdek; yukarı, triceps'i sıkın. Aşağı gel; uzatmak. Yine, tüm hareket aralığından geçmek iyidir. Sadece tepeden geçmek istemezsiniz çünkü bu tüm kası çalıştırmaz. Aşağı gel; şu triceps'i tam buraya gerin. Geri gel; sıkmak. Sıkmak. Ve bu senin baş üstü dambıl tricep uzantın.";
                videoURL = "https://youtu.be/_gsUck-7M74";
                gifID = R.drawable.tricep_dumbell_extension;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/tricepdumbellextension.jpg", false);
                break;
            case "dumbell kickback":
                hakkinda = "Dumbbell kickback hareketi; dumbbell kullanıp, bir ayak bench üstündeyken veya ayaktayken, üst vücudu yere doğru eğerek, dik duran ön kolu, yere paralel şekilde getirip yapılan egzersizdir. Bu hareket arka kol kaslarını geliştirmektedir.";
                videoURL = "https://youtu.be/Qo6Rs4hHy0U";
                gifID = R.drawable.dumbell_kickback;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/dumbellkickback.jpg", false);
                break;
            case "reverse dips":
                hakkinda = "Düz bir bench yardımıyla, kendi vücut ağırlığımızı kullanarak yaptığımız bir arka kol egzersizidir. »Triceps dips ( dips makinesinde barlara tutunarak kendimizi kaldırdığımız) hareketine nazaran yapması daha kolay bir harekettir.\n" +
                        "Vücut geliştirmeye yeni başlayanlar için uygundur. Evde; koltuk, kanepe, sandalye vs kullanılarak yapılabilir. Daha ileri seviye için ayakları bir başka bench’e koyarak yapılabilir. Bir sonraki seviye ise  iki bench + karın bölgemizin üstüne ağırlık plakaları koymaktır.";
                videoURL = "https://youtu.be/OzJ3n7CnhhI";
                gifID = R.drawable.reverse_dips;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/reversedips.jpg", false);
                break;

                //BACAK HAREKETLERİ
            case "lunge":
                hakkinda = "Bacak ve kalça bölgesindeki büyük kas gruplarını inşa etmek amaçlı, Türkçe karşılığı ön hamle olarak bilinen egzersize lunge hareketi denmektedir. Özellikle vücudun alt bölgesini şekillendirmek ve güçlendirmek açısından faydalıdır. Üstelik bu egzersizleri yapmak için spor salonlarına gitmenize de gerek yok. Evde başlangıç seviyesinde lunge hareketine başlayabilir ya da ileri seviye varyasyonlarını farklı metotlarla uygulayarak bu egzersizlerinden farklı şekillerde faydalanabilirsiniz. Hem vücut sağlığınızı iyileştirmek hem de daha sıkı kalça ve bacaklara sahip olmanıza yardımcı olacak lunge egzersizi, kilo kaybına ek olarak simetrik bir vücuda katkıda bulunma gibi faydaları ile ön plana çıkmaktadır.";
                videoURL = "https://youtu.be/qbPLDFf9LfI";
                gifID = R.drawable.lunge;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/lunge.jpg", false);
                break;
            case "squat":
                hakkinda = "Ayakta durma pozisyonu ile çömelerek öne doğru eğilme pozisyonundan oluşan harekete squat denilmektedir. Squat demek kelime anlamı itibari ile \\\"çömelme\\\" demektir. Squat hareketi ile birlikte kalça ve diz kasları sürekli olarak çalışmaktadır. Squat hareketi ağırlık ile de yapılan bir harekettir. Hem ağırlık ile hem de ağırlık olmadan da yapılmaktadır.";
                videoURL = "https://youtu.be/0B-v57Csk44";
                gifID = R.drawable.squat;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/squat.jpg", false);
                break;
            case "leg press":
                hakkinda = "Leg press hareketi esasen leg press makinesi kullanılarak yapılmakta olan kalça ve üst bacak ve geliştirme egzersizi olarak kabul edilmektedir. Varyasyonlar ile dıi bacak, iç bacak ve kalflar da çalıştırılabilmektedir. Oldukça yüksek ağırlıklara girilmediği sürece ve aynı zamanda hareket formu bozulmadığı sürece bir hayli güvenli olmaktadır. Squat’tan sonra gelen en etkili nitelikteki bacak egzersizi olmaktadır. Leg press hareketinin yararlarına bakıldığında ise ön bacak kaslarını iyi düzeyde izole etmesinden dolayı kas gelişimi oldukça etkilidir. Set bitimlerinde çok daha hafif ağırlıklar ile yüksek tekrarlar yapmak da pump etkisini en sonuna kadar hissettirmektedir.";
                videoURL = "https://youtu.be/QDnCR1eOOPw";
                gifID = R.drawable.leg_press;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/legpress.jpg", false);
                break;
            case "leg extension":
                hakkinda = "Bacak geliştirme egzersizleri arasında bulunan Leg Extension, ağırlık makinesi aracılığıyla yapılır. Birbirinden farklı bölümlere sahip olan üst bacağın kısa sürede gelişmesi ve güçlenmesi bu hareket sayesinde mümkün olur. Egzersizin temel özelliği; makine sandalyesine oturup ağırlık barını iki ayak ile yukarı kaldırmaktan ibarettir.";
                videoURL = "https://youtu.be/0EwFf_3niAg";
                gifID = R.drawable.leg_extension;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/legextension.jpg", false);
                break;
            case "leg curl":
                hakkinda = "Leg Curl Hareketi bacak grubunun arka bacak kısmındaki kasları çalıştıran bir harekettir. Bir makine yardımıyla yapılır. Yatarak yapılan arka bacak idmanı yüz üstü şekilde makineye yatılır ve ayaklar pedlere yaslanır eller ilede utma yerlerinden tutulur ayaklar kalçaya doğru çekilir. Bu hareket arka bacak kasına konsantre bir biçimde etki eder ve çalıştırır.";
                videoURL = "https://youtu.be/SbSNUXPRkc8";
                gifID = R.drawable.leg_curl;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/legcurl.jpg", false);
                break;
            case "calf raise":
                hakkinda = "Standing calf raise, kalf kaslarını geliştirmek için yapılan klasik ve standart bir hareketidir. Özellikle de Gastrocnemius kasının çalışmasına yardımcı olur. Bu hareketi yapmak için omuzlarınızı makineye yerleştirmeniz ve parmak uçlarınızın biraz daha ilerisinde durmanız gerekir. Topuğunuzu yere doğru kontrollü bir şekilde basarak indirmeli ve iyice esnemelisiniz. Bu şekilde sadece 2 saniye durmanız gerekir. Sonra tekrar yukarı doğru kaldırmanız gerekir.";
                videoURL = "https://youtu.be/-M4-G8p8fmc";
                gifID = R.drawable.calf_raise;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/calfraise.jpg", false);
                break;

                //OMUZ HAREKETLERİ
            case "dips":
                hakkinda = "Dips hareketi bir barfiks kadar etki sağlayan önemli bir çalışmadır. Özellikle ağır bir yük ile hareket yapıldığında bölge kasları çok daha etkin şekilde çalıştırılabilmektedir. Mutlaka dinlenmiş şekilde ve kas bölgesi çalışmaya hazır olduğunda, yorgun olmayan bir zamanda dips hareketlerinin yapılması gerekir.";
                videoURL = "https://youtu.be/2z8JmcrW-As";
                gifID = R.drawable.dips;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/dips.jpg", false);
                break;
            case "dumbell shoulder press":
                hakkinda = "Dumbbell Shoulder Press egzersizi omuz kasları için çok önemli egzersizler arasında yer alır. Diğer ismi ile dumbbell omuz press egzersizi, vücuda paralel olarak, çene seviyesinde bulunan dumbbell'ları yukarı doğru iterek yapılan orta omuz yani lateral deltoid egzersizidir.";
                videoURL = "https://youtu.be/k9Y8meEPHAA";
                gifID = R.drawable.dumbell_shoulder_press;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/dumbellshoulderpess.jpg", false);
                break;
            case "lateral raise":
                hakkinda = "Lateral raise – dumbbell yana açış hareketi; her iki ele alınan dumbbell'lar yardımıyla, yere sarkık halde duran kolları yanlara açmak suretiyle yapılan lateral deltoid – orta omuz kasını çalıştıran bir omuz egzersizidir.";
                videoURL = "https://youtu.be/a4lcU0WMiIY";
                gifID = R.drawable.lateral_raise;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/lateralraise.jpg", false);
                break;
            case "front raise":
                hakkinda = "Dumbell front raise; omuz egzersizidir. Oturarak ya da ayakta yapılabilmektedir. Omuz eklemleriyle yapılan egzersiz, omzun ön bölümünü çalıştırır. Hareket; çift ya da tek kolla yapılmaktadır. Bu hareket, başlangıç aşamasıyla beraber yapılıyorsa set içerisinde kolları ara sıra çalıştırmak mantıklı olacaktır. Bunun nedeni iki kol beraber kaldırıldığı zaman bele çok yük binmesinden kaynaklıdır.";
                videoURL = "https://youtu.be/-t7fuZ0KhDA";
                gifID = R.drawable.front_raise;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/frontraise.jpg", false);
                break;
            case "rear delt fly":
                hakkinda = "Rear Delt Fly veya Dumbell Reverse Fly olarak da bilinen hareket, üst sırt kaslarınızı ve omuz kaslarınızı, özellikle arka deltoidlerinizi veya arka deltoidlerinizi hedef alan bir ağırlık antrenmanı egzersizidir.";
                videoURL = "https://youtu.be/1FNDEePWTLc";
                gifID = R.drawable.rear_delt_fly;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/reardeltfly.jpg", false);
                break;
            case "facepull":
                hakkinda = "Face Pull, öncelikle omuzlarımızı özellikle de arka omuz başlarını ve arka deltoidleri çalıştırıp güçlendirip ve dayanıklı bir hale getiren egzersizdir. Ayrıca ön kol kaslarımızı da geliştirmektedir. Bir üst vücut egzersizidir. Omuz kaslarını daha güçlü ve daha hacimli görünümünü sağlamak için yapılacak bir egzersizdir.";
                videoURL = "https://youtu.be/rep-qVOkqgk";
                gifID = R.drawable.facepull;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/facepull.jpg", false);
                break;

            //KARIN HAREKETLERİ
            case "plank":
                hakkinda = "Plank hareketi modern egzersiz çeşitleri arasında en popüler hareketlerden biridir. Beden gücü ile herhangi bir ekipmana ihtiyaç duyulmadan gerçekleştirilen bu hareketin ortaya çıkışı ise pilates ve yoga gibi sporlara dayanır. Çapraz, yan ve düz karın kaslarını etkili bir şekilde çalıştıran plank, omurgaya gereksiz yük bindirmeden yapılan ve sırt kaslarını güçlendiren bir egzersiz türüdür. Düzenli bir şekilde yapılan plank hareketi metabolizma hızını artırmanın yanında sırt ağırlarından şikayetçi olanların da ağrılarının azalmasına yardımcı olur.";
                videoURL = "https://youtu.be/F-nQ_KJgfCY";
                gifID = R.drawable.plank;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/plank.jpg", false);
                break;
            case "crunch":
                hakkinda = "“Crunch” en sevilen karın egzersizlerinden bir tanesidir. \"Altılı karın kası\" yapmak ve bel bölgesi yağı eritmek için crunch hareketleri bir zorunluluktur. Bu hareket karın kaslarınızı ayrı tutar, dengenizi geliştirir ve fonksiyonel vücut formunu güçlendirir. Rektus abdominis, tranvers abdominis ve oblikleri hedef alır.  “Crunch” hareketleri karın kaslarınızı güçlendirmeye, duruşunuzu geliştirmeye ve yağ yakmaya yardımcı olur. İleri seviye ağırlık kaldırıcılar daha iddialı bir antrenman için ağırlık levhaları kullanabilir.";
                videoURL = "https://youtu.be/E9-wYvdkjGg";
                gifID = R.drawable.crunch;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/crunches.jpg", false);
                break;
            case "knee to chest":
                hakkinda = "Karın kası dediğimiz zaman birçoğumuzun aklına güzel bir fiziksel görüntü oluşturan baklavalar geliyor. Evet karın kası bize oldukça fit bir görüntü kazandırıyor; fakat karın kasının bize olan faydası sadece bu değil. Karın kasları ve bu bölge adeta bizim merkezimiz. Bu merkezden ise birçok durumda yararlanıyoruz. Karın kasının gelişmiş olması, sağlığımıza bağırsak aktivitelerinin artırılması ve iç organların korunması gibi onlarca fayda sağlıyor.\nKarın kası egzersizlerinin vücut sağlığımız açısından önemi şu şekildedir:\n" +
                        "\n" +
                        "Karın duvarları güçlenir.\n" +
                        "İç organlar daha iyi bir şekilde korunur.\n" +
                        "Soluk alma ve verme desteklenir.\n" +
                        "Vücudun en fazla yağ toplanan bölgesi olan karın bölgesindeki yağların yakımı desteklenir.\n" +
                        "Bazı spor dallarında güç aktarımını destekler. (Boks, tenis, futbol, golf)\n" +
                        "Vücut daha dengeli bir hal alır.\n" +
                        "Diğer egzersizler çok daha kaliteli bir şekilde uygulanır.\n" +
                        "Bel ağrısını ve beldeki incinmeyi azaltır.\n" +
                        "Sırt ve boyun ağrıları için etkilidir.\n" +
                        "Doğru bir postür için vücudun merkezi olan karın kasları oldukça önemlidir.\n" +
                        "Abdominaller yani karın kasları fazla sıvıyı, toksinleri ve yağı vücuttan atmada yardımcı bir görev üstlenmektedir. Eğer karın bölgesindeki kaslarımız yeteri kadar güçlüyse bağırsaklarımız da daha güçlü anlamına gelmektedir. Bu sayede kabızlık gibi problemler azalmaktadır.\n" +
                        "Bir diğer yandan egzersiz türleri ve özellikle karın kası egzersizleri bağırsak reflekslerini tetikleyerek sindirime yardımcı olmaktadır.";
                videoURL = "https://youtu.be/pE9MnR9icAQ";
                gifID = R.drawable.knee_to_chest;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/kneetochest.jpg", false);
                break;
            case "toe tap":
                hakkinda = "Adonis kasını görünür kılmak ve zamanla güçlendirmek için karın kası geliştirme antrenmanlarına öncelik vermek gerekir. Fakat, sadece karın antrenmanları tek başına yeterli değildir. Bu bağlamda vücudun yağ oranını da ideal seviyeye düşürmek gerekir. Her egzersiz rutininde kardiyo ve HIIT antrenmanları sürdürülebilir hale getirilmeli, kondisyon ve dayanıklılık birlikte artırılmalıdır. Daha sonra doğrudan adonis bölgesinin çalışmasını sağlayan karın egzersizleri yapılarak mümkün olan en kısa sürede sonuç alınması sağlanabilir. Tam, yan veya yarım plank pozisyonlarının yanı sıra basit ancak etkili bazı egzersizlerle de adonis kaslarını güçlendirebilirsiniz. “Adonis kası nasıl yapılır evde ya da spor salonunda?” diye merak ediyorsanız, gelin birlikte her yerde yapılabilecek iki etkili adonis egzersizine yakından göz atalım.";
                videoURL = "https://youtu.be/jfXcyLTuKP4";
                gifID = R.drawable.heel_tap;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/heeltap.jpg", false);
                break;
            case "russian twist":
                hakkinda = "Hareket karın kaslarımızın oblik kısımlarını çalıştırmak için yapılmaktadır. V şeklinde oturup üst vücudumuzu sağ ve sola döndürerek yapılan harekettir. Eğer ki başlangıç seviyesinde V şeklinde oturmak zor geliyor ise ayaklarınız yerde olacak bir şekilde hareketi yapabilirsiniz. ";
                videoURL = "https://youtu.be/xdI5hlBLD1I";
                gifID = R.drawable.russian_twist;
                musclesWorked = imageProcess.GetDrawableFromAssets("musclesworked/russiantwist.jpg", false);
                break;
            default:
                gifID = R.drawable.error;
                musclesWorked = AppCompatResources.getDrawable(activity,R.drawable.fare);
                break;

        }
    }


}
