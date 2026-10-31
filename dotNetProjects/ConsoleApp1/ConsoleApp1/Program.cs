namespace ConsoleApp1
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hola, ¿Cómo te llamas?");
            string name = Console.ReadLine();
            DateTime now = DateTime.Now;

            Console.WriteLine($"\nHola {name}. Son las {now:t} del {now:d}");

            Console.ReadKey();
        }
    }
}
