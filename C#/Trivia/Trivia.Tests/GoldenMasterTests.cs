using System;
using System.IO;
using System.Text;
using NUnit.Framework;

namespace Trivia.Tests
{
    [TestFixture]
    public class GoldenMasterTests
    {
        private static readonly Encoding GoldenMasterFileEncoding = Encoding.UTF8;
        
        private TextWriter _oldConsoleOut;

        [SetUp]
        public void RemberOriginalConsoleOut()
        {
            _oldConsoleOut = Console.Out;
        }

        [TearDown]
        public void RestoreOriginalConsoleOut()
        {
            Console.SetOut(_oldConsoleOut);
        }

        [Ignore("only run manually to record golden masters")]
        [Test]
        public void RecordGoldenMasters([Range(0, 255)] int seed)
        {
            var goldenMasterOutput = RunGameAndCaptureOutput(seed);
            Directory.CreateDirectory(OutputDirectoryForGoldenMasterFiles());
            File.WriteAllText(PathToOutputFile(seed), goldenMasterOutput, GoldenMasterFileEncoding);
        }


        [Test]
        public void RunGoldenMasterTests([Range(0, 255)] int seed)
        {
            var goldenMaster = File.ReadAllText(PathToOutputFile(seed), GoldenMasterFileEncoding);
            var actualOutput = RunGameAndCaptureOutput(seed);

            Assert.AreEqual(goldenMaster, actualOutput);
        }

        
        private static string PathToOutputFile(int seed)
        {
            return Path.Combine(OutputDirectoryForGoldenMasterFiles(), seed + ".out");
        }

        private static string OutputDirectoryForGoldenMasterFiles()
        {
            return Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "..", "..", "GoldenMasters");
        }

        private static string RunGameAndCaptureOutput(int seed)
        {
            var stringWriter = new StringWriter();
            Console.SetOut(stringWriter);
            
            GameRunner.RunGame(new Random(seed));

            return stringWriter.ToString();
        }
    }
}