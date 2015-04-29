TowerData Personalization API - C#
====================================
The C# SDK contains two solutions: personalization and example. Personalization is used to compile a DLL containing the
TowerData C# API, while example provides several usage examples of the API. A pre-compiled DLL is provided that should
work with most modern CPUs and Windows operating systems. For sample usage of direct and bulk APIs, see
csharp\example\example\TowerDataExample.cs. The SDK uses the class JavaScriptSerializer, which is available in
.NET Frameworks 3.5 and higher, in the System.Web.Extensions assembly.

Usage
-----
To run the sample solution, open a command prompt and cd to the bin directory of example. For instance,

   cd <path-to-SDK>\csharp\example\example\bin

The usage syntax is as follows

   example.exe <your API key> [sample email 1] [sample email 2] [sample email 3]

The only required parameter is a valid API key. You can also optionally enter up to three sample emails. When you
hit enter, a series of queries will be run. The first query will use sample email 1 (or the default if not entered)
and perform a query by email only. The second query is a bulk query that contains an email and NAP. If entered, sample
email 1 will be used in this query. The third query is querying by email iteratively using three different emails. If
entered, your sample email 1, 2 and 3 will be used for this query. After each query, the results are returned to
the console. You are prompted to "Hit Enter" to proceed to the next query.

Recompiling
-----------
A C# compiler is required if recompilation of either solution is necessary. This SDK was compiled with Microsoft
Visual Studio Community 2013. If using Visual Studio Community 2013, from the Windows Start menu select

   All Programs -> Visual Studio 2013 -> Visual Studio Tools

This will open the Visual Studio Common Tools folder in Windows Explorer. Double click the shortcut

   Developer Command Prompt for VS2013

In the command prompt that opens, cd to the directory containing the solution you want to recompile. For example,

   cd <path-to-SDK>\csharp\personalization

Clean and rebuild the solution with the following command

   msbuild /p:Configuration=Release /p:Platform="Any CPU" /target:Rebuild


License
-------
Copyright 2014 TowerData

* The TowerData Personalization API has separate terms and conditions, which can
  be found at http://intelligence.towerdata.com/terms_and_conditions
* If you send us code, please keep in mind that it will be distributed under
  the same license as the rest of the project.
* This code is licensed under the Apache License which follows...

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
