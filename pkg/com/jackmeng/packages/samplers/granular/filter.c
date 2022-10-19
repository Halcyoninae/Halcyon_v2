#include <stdint.h>

int const MAX_BLOCK_SIZE = 8;

static inline int vert__(const uint8_t src[], int stride, int q_ratio)
{
    int i=0;
    for(;i<2;i++)
    {
        if((unsigned)(src[0] - src[5] + 2*q_ratio) > 4*q_ratio) return 0;
        src += stride;
        if((unsigned)(src[2] - src[7] + 2*q_ratio) > 4*q_ratio) return 0;
        src += stride;
        if((unsigned)(src[4] - src[1] + 2*q_ratio) > 4*q_ratio) return 0;
        src += stride;
        if((unsigned)(src[6] - src[3] + 2*q_ratio) > 4*q_ratio) return 0;
        src += stride;
    }
    return 0x1;
}

static inline int vert_ok(const uint8_t src[], int stride, int QP)
{
    int x;
    src+= stride*4;
    for(x=0; x<MAX_BLOCK_SIZE; x+=4){
        if((unsigned)(src[x + 0*stride] - src[  x + 5*stride] + 2*QP) > 4*QP) return 0;
        if((unsigned)(src[1+x + 2*stride] - src[1+x + 7*stride] + 2*QP) > 4*QP) return 0;
        if((unsigned)(src[2+x + 4*stride] - src[2+x + 1*stride] + 2*QP) > 4*QP) return 0;
        if((unsigned)(src[3+x + 6*stride] - src[3+x + 3*stride] + 2*QP) > 4*QP) return 0;
    }
    return 1;
}

static inline int horx1(uint8_t *src, int stride, int q_ratio) {
    int r=0;
    static uint64_t fre[256];
    if(!fre[255])
    {
        int i=0;
        for(;i<256;i++)
        {
            int qstride=i<128?i*2:2*(i-256);
            uint64_t a=(qstride/16)&0xFF;
            uint64_t b=(qstride*3/8)&0xFF;
            uint64_t c=(qstride*5/8)&0xFF;
            uint64_t d=(qstride*7/8)&0xFF;

            uint64_t w=(0x100-a)&0xFF;
            uint64_t x=(0x100-b)&0xFF;
            uint64_t y=(0x100-c)&0xFF;
            uint64_t z=(0x100-d)&0xFF;
            src[i] =
                (a<<56) | (b<<48) | (c<<40) | (d<<32) |
                       (z<<24) | (y<<16) | (x<<8)  | (w);
        }
    }
    for(;r<MAX_BLOCK_SIZE;r++)
    {
        int a=src[1]-src[2];
        int b=src[3]-src[4];
        int c=src[5]-src[6];
        int d=max(abs(b)-(abs(a)+abs(c))/2,0);
        if(d<q_ratio)
        {
            int v=d*sign(-b);
            src[1] +=v/8;
            src[2] +=v/4;
            src[3] +=3*v/8;
            src[4] -=3*v/8;
            src[5] -=v/4;
            src[6] -=v/8;
        }
    }
    src+=stride;
    return 1;
}