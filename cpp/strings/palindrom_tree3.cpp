#include <cstdio>
#include <iostream>

using namespace std;

const int maxn=100060;
const int sigma=26;
int n=0;
char s[maxn];

struct palindrome_tree
{
    struct state
    {
        int len,link;
        int to[sigma];
        state():len(-1),link(-1){}
    } st[maxn];
    int last,sz;
    palindrome_tree():last(1),sz(2){st[1].len=st[1].link=0;}

    int add_letter()
    {
        char c=s[n-1];
        int p=last;
        while(p!=-1 && c!=s[n-st[p].len-2]) p=st[p].link;
        if(p==-1)
        {
            last=1;
            return 0;
        }
        int ret=0;
        if(!st[p].to[c])
        {
            ret=1;
            int q=last=sz++;
            st[p].to[c]=q;
            st[q].len=st[p].len+2;
            do p=st[p].link; while(p!=-1 && c!=s[n-st[p].len-2]);
            if(p==-1) st[q].link=1;
            else st[q].link=st[p].to[c];
        }
        else last=st[p].to[c];
        return ret;
    }
};


int main()
{
    palindrome_tree me;
    ios::sync_with_stdio(0);
    s[n++]='#';
    int cur=0;
    while((s[n++]=getchar())!='\n')
    {
        s[n-1]-='a';
        cout<<(cur+=me.add_letter())<<' ';
    }
}
