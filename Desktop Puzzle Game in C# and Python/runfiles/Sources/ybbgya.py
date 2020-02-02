import cv2
#import  sys
# from matplotlib import pyplot as plt
img = cv2.imread(('E:\\disk(D)\\YazLab\\yazlab21\\runfiles\\Sources\\src'+(str(2))+'.jpg'),0)
minimum = 9223372036854775807
x,y = 0,0
for i in range (4):
    for j in range (4):
        img2 = cv2.imread(('E:\\bmps\\' + (str(i)) +str(j)+ '.jpg'), 0)
        H1 = cv2.calcHist([img],[0],None,[256],[0,256])
        H2 =  cv2.calcHist([img2],[0],None,[256],[0,256])
        temp = cv2.compareHist(H1, H2, cv2.HISTCMP_CHISQR)
        if(temp < minimum):
            minimum = temp;
            x,y = i,j

# plt.hist(img.ravel(),256,[0,256]); plt.show()
print(str(x)+str(y))
