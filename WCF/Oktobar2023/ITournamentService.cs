using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;

[ServiceContract]
public interface ITournamentService
{

	[OperationContract]
	bool SignIn(Student student, string disciplineId);

    [OperationContract]
    bool SignOut(string studentIndex, string disciplineId);

    [OperationContract]
    List<Discipline> GetAllDisciplinesFor(string studentIndex);

    [OperationContract]
    int GetNumberOfStudentsFor(string disciplineId);

    [OperationContract]
    List<List<Student>> GetAllGroupsFor(string disciplineId);
}

[DataContract]
public class Discipline
{
    [DataMember]
    public string Id { get; set; }

    [DataMember]
    public List<List<Student>> Groups { get; set; }

    internal bool Enroll(Student student)
    {
        if (student == null || IsEnrolled(student.Index))
            return false;

        List<Student> group;
        if (Groups.Count == 0 || Groups.All(g => g.Count == 16))
        {
            group = new List<Student>(16);
            Groups.Add(group);
        }
        else
            group = Groups.Where(g => g.Count < 16).First();

        group.Add(student);

        Rebalance();

        return true;
    }

    internal bool IsEnrolled(string studentIndex)
    {
        // return Groups.Any(g => g.Any(s => s.Index == studentIndex));

        foreach (List<Student> group in Groups)
            foreach (Student student in group)
                if (student.Index == studentIndex)
                    return true;

        return false;
    }

    internal bool Remove(string studentIndex)
    {
        if (!IsEnrolled(studentIndex))
            return false;

        List<Student> group = Groups.Find(g => g.Any(s => s.Index == studentIndex));
        group.RemoveAll(s => s.Index == studentIndex);

        if (group.Count == 0)
            Groups.Remove(group);

        Rebalance();

        return true;
    }

    private void Rebalance()
    {
        List<Student> allStudents = Groups.SelectMany(g => g)
            .OrderBy(s => -s.AverageGrade)
            .ToList();

        Groups.ForEach(g => g.Clear());

        for(int i = allStudents.Count - 1; i >= 0;)
        {
            for (int j = Groups.Count - 1; j >= 0; j--)
            {
                Groups[j].Prepend(allStudents[i]);
                allStudents.RemoveAt(i--);
            }
        }
        
    }
}

[DataContract]
public class Student
{
    [DataMember]
    public string Index { get; set; }

    [DataMember]
    public string FirstName { get; set; }

    [DataMember]
    public string LastName { get; set; }

    [DataMember]
    public decimal AverageGrade { get; set; }
}

