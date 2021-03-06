
ll sqrt(ll a, ll p) {
    a %= p; if (a < 0) a += p;
    if (a == 0) return 0;
    assert(modpow(a, (p-1)/2, p) == 1);
    if (p % 4 == 3) return modpow(a, (p+1)/4, p);
// aˆ(n+3)/8 or 2ˆ(n+3)/8 ∗ 2ˆ(n−1)/4 works i f p % 8 == 5
    ll s = p - 1;
    int r = 0;
    while (s % 2 == 0)
        ++r, s /= 2;
    ll n = 2; // find a non−square mod p
    while (modpow(n, (p - 1) / 2, p) != p - 1) ++n;
    ll x = modpow(a, (s + 1) / 2, p);
    ll b = modpow(a, s, p);
    ll g = modpow(n, s, p);
    for (;;) {
        ll t = b;
        int m = 0;
        for (; m < r; ++m) {
            if (t == 1) break;
            t = t * t % p;
        }
        if (m == 0) return x;
        ll gs = modpow(g, 1 << (r - m - 1), p);
        g = gs * gs % p;
        x = x * gs % p;
        b = b * g % p;
        r = m;
    }
}