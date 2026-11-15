# Tạo ảnh cho Google Play: icon 512, ảnh bìa 1024x500, ảnh chụp màn hình 1080x1920 (mô phỏng giao diện app)
from PIL import Image, ImageDraw, ImageFont
import os
OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "images")
FB = "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf"
FR = "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"
def F(sz, b=False): return ImageFont.truetype(FB if b else FR, sz)
GREEN=(46,125,50); LGREEN=(215,239,216); BG=(243,246,243); INK=(26,28,26); GREY=(74,82,74); LINE=(183,192,183)
AMBER=(255,179,0); ORANGE=(255,112,67); WHITE=(255,255,255); RED=(198,40,40)
SUB={"Toán":(30,136,229),"Tiếng Việt":(230,74,25),"Tiếng Anh":(142,36,170),"Tự nhiên & Xã hội":(46,158,79),"Lịch sử & Địa lí":(141,110,99)}
EMO={"Toán":"123","Tiếng Việt":"Aa","Tiếng Anh":"ABC","Tự nhiên & Xã hội":"TN","Lịch sử & Địa lí":"VN"}

def rr(d,box,r,fill=None,outline=None,w=1): d.rounded_rectangle(box,r,fill=fill,outline=outline,width=w)
def text(d,xy,s,sz,fill=INK,b=False,anchor="la"): d.text(xy,s,font=F(sz,b),fill=fill,anchor=anchor)
def wrap(d,s,sz,maxw,b=False):
    words=s.split(); lines=[]; cur=""
    for w in words:
        t=(cur+" "+w).strip()
        if d.textlength(t,font=F(sz,b))<=maxw: cur=t
        else: lines.append(cur); cur=w
    if cur: lines.append(cur)
    return lines

def icon(size):
    s=size/48.0
    im=Image.new("RGBA",(size,size),(0,0,0,0)); d=ImageDraw.Draw(im)
    P=lambda *a:[v*s for v in a]
    d.rounded_rectangle(P(4,4,44,44),6*s,fill=GREEN)
    d.rounded_rectangle(P(11,9,35,39),2*s,fill=WHITE)
    d.rounded_rectangle(P(11,9,35,15),2*s,fill=AMBER); d.rectangle(P(11,12,35,15),fill=AMBER)
    for y,x2 in ((22,29),(28,25),(34,23)): d.rectangle(P(15,y,x2,y+2),fill=(176,190,197))
    d.polygon(P(27,31,29.2,28.8,31.5,31.1,36.8,25.8,39,28,31.5,35.5),fill=(67,160,71))
    d.polygon(P(34,40,42,26,45,27.7,37,41.7),fill=ORANGE)
    d.polygon(P(34,40,33.2,43.8,37,41.7),fill=(55,71,79))
    return im

def phone(title_bar=True):
    im=Image.new("RGB",(1080,1920),BG); d=ImageDraw.Draw(im)
    text(d,(54,34),"9:41",34,GREY,True)
    text(d,(1026,34),"100%",30,GREY,False,"ra")
    return im,d

def topbar(d,title,sub=None,color=GREEN):
    d.rectangle((0,100,1080,300),fill=color)
    text(d,(54,150),title,54,WHITE,True)
    if sub: text(d,(54,230),sub,34,(235,245,235))

def screen_home():
    im,d=phone(); topbar(d,"Bài Tập Tuần","Tiểu học · Lớp 1 đến lớp 5")
    text(d,(54,350),"Chọn môn học",44,INK,True)
    y=430
    for name,col in SUB.items():
        rr(d,(54,y,1026,y+210),32,WHITE,LINE,2)
        rr(d,(54,y,78,y+210),12,col)
        rr(d,(110,y+40,240,y+170),36,tuple(int(c*0.15+255*0.85) for c in col))
        text(d,(175,y+105),EMO[name],44 if len(EMO[name])<4 else 36,col,True,"mm")
        text(d,(280,y+50),name,46,INK,True)
        tag={"Toán":"Tính nhẩm, tư duy, giải toán","Tiếng Việt":"Chính tả, từ ngữ, đọc hiểu","Tiếng Anh":"Từ vựng, ngữ pháp, giao tiếp",
             "Tự nhiên & Xã hội":"Tự nhiên – Xã hội, Khoa học","Lịch sử & Địa lí":"Lớp 4 và lớp 5"}[name]
        text(d,(280,y+120),tag,32,GREY)
        y+=240
    return im

def screen_weeks():
    im,d=phone(); topbar(d,"Toán · Lớp 3","35 tuần + đề giữa kỳ, cuối kỳ",SUB["Toán"])
    text(d,(54,350),"Chọn tuần",44,INK,True)
    n=1; y=430
    for r in range(8):
        for c in range(4):
            x=54+c*246
            done=n<=9; best=n in(3,5)
            fill=LGREEN if done else WHITE
            rr(d,(x,y,x+222,y+170),28,fill,GREEN if done else LINE,3)
            text(d,(x+111,y+62),f"Tuần {n}",38,INK,True,"mm")
            text(d,(x+111,y+120),("✓ 40/40" if best else "✓ đã làm") if done else "40 câu",28,GREEN if done else GREY,False,"mm")
            n+=1
        y+=195
        if n>32: break
    rr(d,(54,y+10,1026,y+130),30,GREEN); text(d,(540,y+70),"Đề giữa kỳ · Đề cuối kỳ",42,WHITE,True,"mm")
    return im

def screen_mode():
    im,d=phone(); topbar(d,"Toán · Lớp 3 · Tuần 5","Chọn cách làm bài")
    cards=[("Báo đúng sai ngay","Chọn xong là biết đúng hay sai, nghe giọng đọc giải thích. Không đổi được đáp án.",GREEN,"✓"),
           ("Làm xong mới biết","Được đổi đáp án thoải mái bằng nút Trước, Tiếp. Nộp bài rồi mới xem kết quả.",(30,136,229),"✎")]
    y=380
    for t,s,col,ic in cards:
        rr(d,(54,y,1026,y+520),40,WHITE,col,5)
        rr(d,(110,y+60,260,y+210),75,tuple(int(c*0.15+255*0.85) for c in col)); text(d,(185,y+135),ic,80,col,True,"mm")
        text(d,(300,y+90),t,50,INK,True)
        for i,l in enumerate(wrap(d,s,38,680)): text(d,(300,y+165+i*52),l,38,GREY)
        rr(d,(110,y+390,970,y+480),30,col); text(d,(540,y+435),"Bắt đầu",42,WHITE,True,"mm")
        y+=570
    text(d,(540,1590),"Câu hỏi được đọc to tự động khi vào từng câu",34,GREY,False,"mm")
    return im

def quiz(feedback):
    im,d=phone(); col=SUB["Toán"]
    d.rectangle((0,100,1080,230),fill=col)
    text(d,(54,140),"Toán · Tuần 5",44,WHITE,True); text(d,(1026,145),"58:12",40,WHITE,True,"ra")
    rr(d,(54,260,1026,330),35,WHITE,LINE,2); text(d,(540,295),"Đã làm 6/40  ▾",34,GREY,False,"mm")
    rr(d,(54,350,1026,372),11,(228,234,228)); rr(d,(54,350,54+972*7//40,372),11,col)
    rr(d,(54,410,1026,1000),36,WHITE,LINE,2)
    text(d,(100,450),"Câu 7",40,col,True)
    rr(d,(830,445,980,505),28,LGREEN); text(d,(905,475),"Đọc",30,GREEN,True,"mm")
    q="Mẹ mua 3 túi kẹo, mỗi túi có 12 cái. Mẹ chia đều số kẹo đó cho 4 bạn. Mỗi bạn được mấy cái kẹo?"
    for i,l in enumerate(wrap(d,q,46,880,True)): text(d,(100,540+i*66),l,46,INK,True)
    if feedback:
        rr(d,(54,1030,1026,1290),30,(232,245,233),GREEN,3)
        text(d,(100,1065),"Chính xác!",48,GREEN,True)
        for i,l in enumerate(wrap(d,"Có tất cả 3 × 12 = 36 cái kẹo. Chia đều cho 4 bạn: 36 : 4 = 9. Mỗi bạn được 9 cái.",36,880)):
            text(d,(100,1135+i*50),l,36,INK)
    opts=["8 cái","9 cái","10 cái","12 cái"]; sel=1
    ys=1340
    for i,o in enumerate(opts):
        x=54+(i%2)*498; y=ys+(i//2)*150
        if feedback and i==1: f,o_,tc=(200,230,201),GREEN,GREEN
        elif not feedback and i==1: f,o_,tc=LGREEN,GREEN,GREEN
        else: f,o_,tc=WHITE,LINE,INK
        rr(d,(x,y,x+474,y+130),30,f,o_,5 if i==1 else 2)
        text(d,(x+60,y+65),"ABCD"[i],40,tc,True,"mm"); text(d,(x+120,y+65),o,44,tc,True,"lm")
    y=1660
    rr(d,(54,y,330,y+120),30,WHITE,LINE,3); text(d,(192,y+60),"Trước",42,GREY,True,"mm")
    rr(d,(354,y,726,y+120),30,GREEN); text(d,(540,y+60),"Nộp",42,WHITE,True,"mm")
    rr(d,(750,y,1026,y+120),30,col); text(d,(888,y+60),"Tiếp",42,WHITE,True,"mm")
    return im

def screen_result():
    im,d=phone(); topbar(d,"Kết quả","Toán · Lớp 3 · Tuần 5")
    rr(d,(54,350,1026,820),44,WHITE,LINE,2)
    d.ellipse((340,390,740,790),fill=LGREEN,outline=GREEN,width=14)
    text(d,(540,560),"36/40",110,GREEN,True,"mm"); text(d,(540,670),"9,0 điểm",50,INK,True,"mm")
    text(d,(540,880),"Rất tốt! Em làm bài chắc chắn.",44,INK,True,"mm")
    for i,(a,b,c) in enumerate((("Đúng","36",GREEN),("Sai","4",RED),("Thời gian","14 phút",GREY))):
        x=54+i*330
        rr(d,(x,940,x+312,1100),30,WHITE,LINE,2); text(d,(x+156,1000),b,56,c,True,"mm"); text(d,(x+156,1065),a,32,GREY,False,"mm")
    text(d,(54,1160),"Các câu làm sai",44,INK,True)
    items=[("Câu 12","18 × 5 = ?","Đáp án: 90"),("Câu 23","Số liền sau của 999 là?","Đáp án: 1000"),("Câu 31","1/4 của 28 là?","Đáp án: 7")]
    y=1230
    for a,b,c in items:
        rr(d,(54,y,1026,y+130,),28,WHITE,LINE,2); rr(d,(54,y,78,y+130),12,RED)
        text(d,(110,y+25),f"{a}: {b}",38,INK,True); text(d,(110,y+75),c,32,GREEN,True); y+=150
    rr(d,(54,1700,520,1810),30,GREEN); text(d,(287,1755),"Làm lại",42,WHITE,True,"mm")
    rr(d,(560,1700,1026,1810),30,WHITE,GREEN,4); text(d,(793,1755),"Xem lại câu sai",38,GREEN,True,"mm")
    return im

def screen_english():
    im,d=phone(); col=SUB["Tiếng Anh"]
    d.rectangle((0,100,1080,230),fill=col)
    text(d,(54,140),"Tiếng Anh · Tuần 12",44,WHITE,True); text(d,(1026,145),"41:30",40,WHITE,True,"ra")
    rr(d,(54,260,1026,330),35,WHITE,LINE,2); text(d,(540,295),"Đã làm 14/40  ▾",34,GREY,False,"mm")
    rr(d,(54,350,1026,372),11,(228,234,228)); rr(d,(54,350,54+972*15//40,372),11,col)
    rr(d,(54,410,1026,1000),36,WHITE,LINE,2); text(d,(100,450),"Câu 15",40,col,True)
    rr(d,(830,445,980,505),28,(240,225,245)); text(d,(905,475),"Đọc",30,col,True,"mm")
    for i,l in enumerate(wrap(d,"Chọn từ đúng điền vào chỗ trống:  She ____ to school every day.",46,880,True)): text(d,(100,550+i*66),l,46,INK,True)
    opts=["go","goes","going","gone"]
    for i,o in enumerate(opts):
        x=54+(i%2)*498; y=1340+(i//2)*150; sel=(i==1)
        rr(d,(x,y,x+474,y+130),30,(240,225,245) if sel else WHITE,col if sel else LINE,5 if sel else 2)
        text(d,(x+60,y+65),"ABCD"[i],40,col if sel else INK,True,"mm"); text(d,(x+120,y+65),o,44,col if sel else INK,True,"lm")
    y=1660
    rr(d,(54,y,330,y+120),30,WHITE,LINE,3); text(d,(192,y+60),"Trước",42,GREY,True,"mm")
    rr(d,(354,y,726,y+120),30,GREEN); text(d,(540,y+60),"Nộp",42,WHITE,True,"mm")
    rr(d,(750,y,1026,y+120),30,col); text(d,(888,y+60),"Tiếp",42,WHITE,True,"mm")
    return im

def feature():
    im=Image.new("RGB",(1024,500),GREEN); d=ImageDraw.Draw(im)
    for i in range(500):
        t=i/500; d.line((0,i,1024,i),fill=(int(46+(30-46)*t),int(125+(100-125)*t),int(50+(40-50)*t)))
    ic=icon(300); im.paste(ic,(70,100),ic)
    text(d,(420,120),"Bài Tập Tuần",74,WHITE,True)
    text(d,(420,215),"Tiểu học",74,AMBER,True)
    text(d,(420,330),"5 môn · Lớp 1 đến lớp 5",36,WHITE)
    text(d,(420,385),"Có đọc câu hỏi bằng tiếng Việt",36,(220,240,220))
    return im

os.makedirs(OUT,exist_ok=True)
icon(512).save(f"{OUT}/icon-512.png")
feature().save(f"{OUT}/feature-graphic-1024x500.png")
shots=[("1-chon-mon",screen_home()),("2-chon-tuan",screen_weeks()),("3-che-do-lam-bai",screen_mode()),
       ("4-cau-hoi-bao-dung-sai",quiz(True)),("5-cau-hoi-tieng-anh",screen_english()),("6-ket-qua",screen_result())]
for n,im in shots: im.save(f"{OUT}/phone-{n}.png")
print("ok")
