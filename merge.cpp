#include <iostream>

using namespace std;

void merge(int a[], int low, int mid, int high)
{
    int n1 = mid - low + 1;
    int n2 = high - mid;

    int a1[n1], a2[n2];

//add elements to subarr
    for (int i = 0; i < n1; i++)
    {
        a1[i] = a[low + i];
    }
    for (int i = 0; i < n2; i++)
    {
        a2[i] = a[mid + 1 + i];
    }
    // maintain current index of subarray
    int cur1 = 0, cur2 = 0, index = low;

    while (cur1 < n1 && cur2 < n2)
    {
        if (a1[cur1] <= a2[cur2])
        {
            a[index] = a1[cur1];
            cur1++;
        }
        else
        {
            a[index] = a2[cur2];
            cur2++;
        }
        index++;
    }
        // Copy the remaining elements of a1 and a2 if any
    while (cur1 < n1)
    {
        a[index] = a1[cur1];
        cur1++;
        index++;
    }
    while (cur2 < n2)
    {
        a[index] = a2[cur2];
        cur2++;
        index++;
    }
}
void mergeSort(int a[], int low, int high)
{
    if(low == high) return;
    
    else
    {
        int mid = (high + low) / 2;

        mergeSort(a, low, mid);
        mergeSort(a, mid + 1, high);

        merge(a, low, mid, high);
    }
}

void print(int a[], int size)
{
    for (int i = 0; i < size; i++)
    {
        cout << a[i] << " ";
    }
    cout << endl;
}
int main()
{
    int a[1000];
    int n;
    cin >> n;
    for (int i = 0; i < n; i++)
    {
        cin >> a[i];
    }
    mergeSort(a, 0, n - 1);
    print(a, n);
}