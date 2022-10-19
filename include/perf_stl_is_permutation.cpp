

#include <algorithm>
#include <iostream>
#include <numeric>
#include <vector>

#include "perf.hpp"

int rand_int()
{
    return static_cast<int>((rand() / double(RAND_MAX)) * 25.0);
}

int main(int argc, char *argv[])
{
    perf_parse_args(argc, argv);
    std::cout << "size: " << PERF_N << std::endl;

    // create vector of random numbers on the host
    std::vector<int> host_vector(PERF_N);
    std::generate(host_vector.begin(), host_vector.end(), rand_int);

    std::vector<int> host_vector2(PERF_N);
    std::copy(host_vector.rbegin(), host_vector.rend(), host_vector2.begin());

    perf_timer t;
    for(size_t trial = 0; trial < PERF_TRIALS; trial++){
        t.start();
        std::is_permutation(host_vector.begin(), host_vector.end(),
                            host_vector2.begin());
        t.stop();
    }
    std::cout << "time: " << t.min_time() / 1e6 << " ms" << std::endl;

    return 0;
}
