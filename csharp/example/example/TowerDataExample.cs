using System.Collections.Generic;
using System;
using System.Net;
using Personalization;

namespace example
{
    class TowerDataExample
    {
        // outlined below are three separate ways of querying the API using the TowerDataApi library
        public static void Main(string[] args)
        {
            TowerDataApi api = (args.Length >= 1 && args[0] != null) ? new TowerDataApi(args[0]) : new TowerDataApi("YOUR_API_KEY");
            String email = (args.Length >= 2 && args[1] != null) ? args[1] : "pete@rapleafdemo.com";
            String email1 = (args.Length >= 3 && args[2] != null) ? args[2] : "steve@rapleafdemo.com";
            String email2 = (args.Length >= 4 && args[3] != null) ? args[3] : "caitlin@rapleafdemo.com";

            // Query by email
            Console.WriteLine("\nQuery by email: "+email+"\n");
            try
            {
                Dictionary<string, Object> response = api.queryByEmail(email);
                foreach (KeyValuePair<string, Object> kvp in response)
                {
                    printKeyValuePair(kvp);
                }
            }
            catch (WebException e)
            {
                Console.WriteLine(e.Message);
            }
            Console.WriteLine("\nHit Enter for the next method");
            Console.ReadLine();

            // Bulk Query
            Console.WriteLine("\nBulk Query with email and NAP\n");
            try
            {
                List<Dictionary<string, Object>> response = api.genericBulkQuery(new List<Dictionary<String, String>> {
                    new Dictionary<String, String> {
                        {"email", email }
                    },
                    new Dictionary<String, String> {
                        {"first", "Pete" },
                        {"last", "Schlick" },
                        {"street", "112134 Leavenworth Rd" },
                        {"city", "San Francisco" },
                        {"state", "CA" },
                        {"zip", "21044"}
                    }
                });
                foreach (Dictionary<string, Object> entry in response)
                {
                    Console.WriteLine("--- entry ---");
                    foreach (KeyValuePair<string, Object> kvp in entry)
                    {
                        printKeyValuePair(kvp);
                    }
                }
            }
            catch (WebException e)
            {
                Console.WriteLine(e.Message);
            }
            Console.WriteLine("\nHit Enter for the next method");
            Console.ReadLine();

            // Query by email iteratively
            Console.WriteLine("\nQuery by email iteratively\n");
            try
            {
                List<Dictionary<string, Object>> response = api.queryByEmail(new List<string> { email, email1, email2 }, true);
                foreach (Dictionary<string, Object> entry in response)
                {
                    Console.WriteLine("--- entry ---");
                    foreach (KeyValuePair<string, Object> kvp in entry)
                    {
                        printKeyValuePair(kvp);
                    }
                }
            }
            catch (WebException e)
            {
                Console.WriteLine(e.Message);
            }
            Console.ReadLine();
        }

        private static void printKeyValuePair(KeyValuePair<String, Object> kvp)
        {
            if (kvp.Value is Dictionary<String, Object>)
            {
                Console.WriteLine("--" + kvp.Key + "--");
                foreach (KeyValuePair<string, Object> sub_kvp in (Dictionary<String, Object>)kvp.Value)
                {
                    printKeyValuePair(sub_kvp);
                }
                Console.WriteLine("--" + kvp.Key + "--");
            }
            else
            {
                Console.WriteLine("{0}: {1}", kvp.Key, kvp.Value);
            }
        }
    }
}
