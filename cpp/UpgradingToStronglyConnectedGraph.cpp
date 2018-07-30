#include <cstdio>
#include <vector>
using namespace std;

#define MAX 12000
bool in[MAX],  // in[c] == true  ��������, ��� ���� �������� ���� � �.�. c
     out[MAX], // out[c] == true  ��������, ��� ���� ��������� ���� �� �.�. c
     dead[MAX]; 

vector<int> s[MAX],     // s[v] = ��������� ������� ������ ������� v.
            s_rev[MAX], // s_rev[v] = ��������� ������� ������ ������� v 
                        //           � ����������������� �����.
            q,          // ��������� ���� ������ � ������� ����� ������� ������ �� ���
                        // ��� ������ ����� � �������.
            m_a, m_b;   // 

int c[MAX],
    cur_c,
    next_s[MAX],
    vis[MAX],
    next_vis;

/* ����� � ������� ���� ������, ���������� �� ������� x.
 * � ������ q ��� ����� �������� � ������� ����������� "������� ������".
 */
void dfs(int x) {
  vis[x] = next_vis; // "�����������" ��� ������� "������" next_vis
  for (int i = 0; i < s[x].size(); i++) 
    if ( !vis[s[x][i]] ) dfs( s[x][i] );
  q.push_back(x);
}

/* ����� � ������� ���� ������, ���������� �� ������� x �
 * ����������������� �����.
 */
void dfs_rev(int x) {
  c[x] = cur_c; // "�����������" ��� ������� "������" cur_c
  for (int i = 0; i < s_rev[x].size(); i++) 
    if ( !c[s_rev[x][i]] ) 
       dfs_rev(s_rev[x][i]);
}

bool match(int x) {
  if ( dead[x] || vis[x] == next_vis ) return false;

  vis[x] = next_vis;

  if ( !out[ c[x] ] ) {
    m_b.push_back(x);
    out[ c[x] ] = true;
    return true;
  }

  while ( next_s[x] < s[x].size() && !match( s[x][next_s[x]] ) ) 
    next_s[x]++;
  return !(dead[x] = next_s[x] == s[x].size());
}

int main() {
  int n, // ����� ������ 
      m, // ����� ����
      i, j, a = 0, b = 0;
  
  // ��������� ������� ������
  scanf("%d %d", &n, &m);
  while ( m-- ) {
    scanf("%d %d", &i, &j);
    s[i].push_back(j);
    s_rev[j].push_back(i);
  }

  // ������� ��� ������� ������� "� �������". 
  // � �������� �� � ������ q � ������� ������ �� ��� ����������� ��������� ������. 
  next_vis = 1; 
  for (i = 0; i < n; i++) if ( !vis[i] ) dfs(i);

  // ������� ��� ������� ������� "� �������" � ����������������� �����. 
  // ����� ������� � ������� ���������� ������� "������ �� ���" 
  // �� ����� ������� ������.
  // ���� c[v] ������� v ���� ����� ������� ����������.
  for (i = n - 1; i >= 0; i--)
    if ( !c[q[i]] ) { cur_c++; dfs_rev(q[i]); }

  // ������� ������� ���������� � ������� ���-�� ������
  // � ������� ���������� �� ������� ���-�� ������� � ������ ����������.
  for (i = 0; i < n; i++)
    for (j = 0; j < s[i].size(); j++)
      if (c[i] != c[s[i][j]])
        out[c[i]] = in[c[s[i][j]]] = true;

  if (cur_c == 1) { printf("0\n"); return 0; }

  for (i = 1; i <= cur_c; i++) { a += !out[i]; b += !in[i]; }
  printf("%d\n", a >? b); //  �������� �������� "a >? b" ���� max(a,b).

  for (i = 0; i < n; i++) {
    next_vis++;
    if ( !in[c[i]] && !dead[i] && match(i) ) {
      m_a.push_back(i);
      in[ c[i] ] = true;
    }
  }

  for (i = 0; i < m_a.size(); i++ ) 
    printf("%d %d\n", m_b[i], m_a[(i+1)%m_a.size()] );

  vector<int> A, B;
  for (i = 0; i < n; i++) {
    if ( !in[c[i]] ) A.push_back(i);
    in[c[i]] = true;
  }

  for (i = 0; i < n; i++) {
    if (!out[c[i]]) B.push_back(i);
    out[c[i]] = true;
  }

  while ( A.size() && B.size() ) {
    printf("%d %d\n", B.back(), A.back());
    A.pop_back(); B.pop_back();
  }

  while ( A.size() ) {
    printf("%d %d\n", m_b.back(), A.back());
    A.pop_back();
  }

  while ( B.size() ) {
    printf("%d %d\n", B.back(), m_a.back());
    B.pop_back();
  }
  
  return 0;
}