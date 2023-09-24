using System.Collections.Generic;
using System.Linq;

public class TournamentService : ITournamentService
{
    private static Dictionary<string, Discipline> _disciplines = new Dictionary<string, Discipline>(10);

    public List<Discipline> GetAllDisciplinesFor(string studentIndex)
    {
        return _disciplines.Select(keyValue => keyValue.Value)
            .Where(discipline => discipline.IsEnrolled(studentIndex))
            .ToList();
    }

    public List<List<Student>> GetAllGroupsFor(string disciplineId)
    {
        Discipline discipline;

        if (!string.IsNullOrWhiteSpace(disciplineId) && _disciplines.TryGetValue(disciplineId, out discipline))
            return discipline.Groups;

        return new List<List<Student>>();
    }

    public int GetNumberOfStudentsFor(string disciplineId)
    {
        Discipline discipline;

        if (string.IsNullOrWhiteSpace(disciplineId) || !_disciplines.TryGetValue(disciplineId, out discipline))
            return 0;

        return discipline.Groups.SelectMany(s => s).Count();
    }

    public bool SignIn(Student student, string disciplineId)
    {
        Discipline discipline;

        if (!_disciplines.TryGetValue(disciplineId, out discipline))
            discipline = new Discipline() { Id = disciplineId, Groups = new List<List<Student>>() };

        return discipline.Enroll(student);
    }

    public bool SignOut(string studentIndex, string disciplineId)
    {
        Discipline discipline;

        if (!_disciplines.TryGetValue(disciplineId, out discipline))
            return false;

        return discipline.Remove(studentIndex);
    }
}
