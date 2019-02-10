#include <iostream>
#include <cstring>
#include <vector>
#include <string>
#include <cstdio>
using namespace std;

const int maxn = 10100100;

int len[maxn];
int suffLink[maxn];
int to[maxn][2];
int cnt[maxn];
int numV;
char str[maxn];

int v;

void addLetter(int n)
{
    while (str[n - len[v] - 1] != str[n] )
        v = suffLink[v];
    int u = suffLink[v];
    while (str[n - len[u] - 1] != str[n] )
        u = suffLink[u];
    int u_ = to[u][str[n] - 'a'];
    int v_ = to[v][str[n] - 'a'];
    if (v_ == -1)
    {
        v_ = to[v][str[n] - 'a'] = numV;
        len[numV++] = len[v] + 2;
        suffLink[v_] = u_;
    }
    v = v_;
    cnt[v]++;
}

void init()
{
    memset(to, -1, sizeof to);
    str[0] = '#';
    len[0] = -1;
    len[1] = 0;
    len[2] = len[3] = 1;
    suffLink[1] = 0;
    suffLink[0] = 0;
    suffLink[2] = 1;
    suffLink[3] = 1;
    to[0][0] = 2;
    to[0][1] = 3;
    numV = 4;
}

int main()
{
    init();
    scanf("%s", str + 1);
    int n = strlen(str);
    for (int i = 1; i < n; i++)
        addLetter(i);

    long long ans = 0;
    for (int i = numV - 1; i > 0; i--)
    {
        cnt[suffLink[i] ] += cnt[i];
        ans = max(ans, cnt[i] * 1LL * len[i] );
//              fprintf(stderr, "i = %d, cnt = %d, len = %d\n", i, cnt[i], len[i] );
    }
    printf("%lld\n", ans);

    return 0;
}