clear all
pos = load('Positions.txt');
t = pos(1:end,1);
xmin=zeros([1,length(t)]);
xmax=xmin;
ymin=xmin;
ymax=xmin;
xmean=xmin;
ymean=xmin;
xstd=xmin;
ystd=xmin;
for(k=1:length(t))
    xmin(k)=min(pos(k,2:2:end));
    xmax(k)=max(pos(k,2:2:end));
    xmean(k)=mean(pos(k,2:2:end));
    xstd(k)=std(pos(k,2:2:end));
    ymin(k)=min(pos(k,3:2:end));
    ymax(k)=max(pos(k,3:2:end));
    ymean(k)=mean(pos(k,3:2:end));
    ystd(k)=std(pos(k,3:2:end));

end
figure(1)
for(k=1:length(t))
    set(gcf,'DoubleBuffer','On')
    plot(pos(k,2:2:end),pos(k,3:2:end),'*')
    axis([min(xmin) max(xmax) min(ymin) max(ymax)])
    drawnow
    pause(0.01)
end

figure(2)
hold on
plot(t,xmin,'r')
plot(t,ymin,'b')
plot(t,xmax,'r')
plot(t,ymax,'b')
title('Största resp minsta x- och y-värde som funktion av t')
legend('x','y')

figure(3)
hold on
plot(t,xmean,'r');
plot(t,ymean,'b');
title('Medelvärde av x resp y som funktion av t')
legend('x','y')

figure(4)
hold on
plot(t, xstd,'r')
plot(t, ystd,'b')
title('Standardavvikelse av x resp y som funktion av t')
legend('x','y')
