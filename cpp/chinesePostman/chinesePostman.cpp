#include <cmath> 
#include <string> 
#include <vector> 
#include <map> 
#include <set> 
#include <cstring> 
#include <iostream> 
#include <cstdio> 
#include <cmath> 
#include <algorithm> 
#include <utility> 
#include <queue> 
#include <functional> 
#include <cstdlib> 
#include <climits> 
#include <sstream> 
#include <cctype> 
#include <complex> 
#include <numeric> 
#include <cassert> 
using namespace std; 

int d[60][60]; 

const long long OO = 1000000000000000LL; 
typedef vector<int> VI; 
typedef vector<long long> VD; 

/* returns the maximum weight perfect matching on a complete bipartite graph */ 
/* matching[i] contains the 0-based number of the second set that has been matched to i */ 
/* runtime: O(V^3) */ 
long long MaxWeightPerfectMatching( vector<VD> &val, VI &matching ) { 
  int n = val.size(); 
  vector< int > matched( n, -1 ); 
  matching.assign( n, -1 ); 
  vector< long long > l1( n, OO ), l2( n, -OO ); 
  for( int k = 0; k < n; k++ ) { 
    vector< int > s( n, 0 ), t( n, 0 ); 
    vector< long long > slick( n, OO ); 
    vector< int > pred( n ); 
    for( int i = 0; i < n; i++ )   // unmatched nodes -> S 
      if( matching[i] < 0 ) { 
        for( int j = 0; j < n; j++ ) 
          slick[j] <?= l1[i] + l2[j] - val[i][j]; 
        s[i] = 1; 
      } 
    int j_min = -1; 
    while( j_min == -1 ) { 
      long long slick_min = OO; 
      for( int j = 0; j < n; j++ ) 
        if( !t[j] && ( slick[j] < slick_min ) ) 
          slick_min = slick[j], j_min = j; 
      assert(j_min>=0); 
      for( int i = 0; i < n; i++ ) { // update labels 
        if( s[i] ) l1[i] -= slick_min; 
        if( t[i] ) l2[i] += slick_min; 
        else slick[i] -= slick_min; 
        if( s[i] && l1[i] + l2[j_min] == val[i][j_min] ) 
          pred[j_min] = i; 
      } 
      if( matched[ j_min ] >= 0 ) { // matching exists already 
        t[j_min] = 1, s[matched[j_min]] = 1; 
        for( int j = 0; j < n; j++ ) // update slick 
          slick[j]<?=l1[matched[j_min]]+l2[j]-val[matched[j_min]][j]; 
        j_min = -1; 
      } 
    } 
    while( j_min >= 0 ) { 
      int i = pred[j_min], j = j_min; 
      j_min = matching[i]; 
      matching[i] = j, matched[j] = i; 
    } 
  } 
  long long weight = 0; 
  for( int i = 0; i < n; i++ ) weight += val[i][matching[i]]; 
  return weight; 
} 

char needvisit[60]; 
char visit[60]; 
int n; 

void dfs(int cur) { 
  if (visit[cur]) 
    return; 
  visit[cur] = 1; 
  for (int i=0; i<n; ++i) 
    if (d[cur][i]) 
      dfs(i); 
} 

class SnowPlow { 
public: 
  int solve(vector <string> roads) { 
    n = roads.size(); 
    int indeg[60] = {0}, outdeg[60] = {0}; 
    int res = 0; 
    for (int i=0; i<n; ++i) 
      for (int j=0; j<n; ++j) { 
        if (roads[i][j] > '0') { 
          d[i][j] = 1; 
          needvisit[i] = needvisit[j] = 1; 
        } 
        else 
          d[i][j] = 0; 
        indeg[j] += roads[i][j]-'0'; 
        outdeg[i] += roads[i][j]-'0'; 
        res += roads[i][j] - '0'; 
      } 
    dfs(0); 
    for (int i=0; i<n; ++i) 
      if (!visit[i] && needvisit[i]) 
        return -1; 
    for (int i=0; i<n; ++i) 
      for (int j=0; j<n; ++j) { 
        if (!d[j][i]) continue; 
        for (int k=0; k<n; ++k) { 
          if (!d[i][k]) continue; 
          if (d[j][k] == 0 || d[j][k] < d[j][i] + d[i][k]) 
            d[j][k] = d[j][i] + d[i][k]; 
        } 
      } 
    vector<int> left, right; 
    for (int i=0; i<n; ++i) { 
      while(indeg[i] < outdeg[i]) { 
        right.push_back(i); 
        ++indeg[i]; 
      } 
      while(outdeg[i] < indeg[i]) { 
        left.push_back(i); 
        ++outdeg[i]; 
      } 
    } 
    assert(left.size() == right.size()); 
    vector<VD> val(left.size(), VD(right.size(), -OO)); 
    for (int i=0; i<(int)left.size(); ++i) 
      for (int j=0; j<(int)right.size(); ++j) 
        if (d[left[i]][right[j]] > 0) 
          val[i][j] = -d[left[i]][right[j]]; 
    VI matching; 
    long long tres = -MaxWeightPerfectMatching(val, matching); 
    if (tres >= OO) 
      return -1; 
    return res + tres; 
  } 
};