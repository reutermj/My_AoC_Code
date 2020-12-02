using System;
using System.Collections.Generic;
using System.Linq;

namespace JoshuaPuzzle
{
    class EatAss
    {
        static void GenerateCombinations(int depth, int loc, List<int> numbers, List<int> combination, List<List<int>> accumulator)
        {
            for(int i = loc; i <= numbers.Count - depth; i++)
            {
                List<int> copy = new List<int>(combination);
                if (depth == 1)
                {
                    copy.Add(numbers[i]);
                    accumulator.Add(copy);
                }
                else
                {
                    copy.Add(numbers[i]);
                    GenerateCombinations(depth - 1, i + 1, numbers, copy, accumulator);
                }
            }
        }

        static List<List<int>> Combinations(List<int> numbers, int p)
        {
            List<List<int>> accumulator = new List<List<int>>();
            GenerateCombinations(p, 0, numbers, new List<int>(), accumulator);
            return accumulator;
        }

        static void Main()
        {
            IEnumerable<int> products =
                Combinations(new List<int>() { 1721, 979, 366, 299, 675, 1456 }, 3)
                    .Where(l => l.Sum() == 2020)
                    .Select(l => l.Aggregate(1, (x, y) => x * y));
            Console.WriteLine(products.First());
        }
    }
}
