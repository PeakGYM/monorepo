"""
def id               = column[String]("id", O.PrimaryKey)
    def name             = column[String]("name")
    def muscleGroup      = column[List[String]]("muscle_group")
    def coach            = column[Option[CoachId]]("coach_id")
    def client           = column[ClientId]("client_id")
    def dateFrom         = column[ZonedDateTime]("time_from")
    def dateTo           = column[ZonedDateTime]("time_to")
    def plannedExercises = column[List[PlannedExercise]]("planned_exercises")
    def inperson         = column[Boolean]("inperson")
"""
import datetime
from random import choice
from random import randrange
import json

seriesCounter = 0
PlannedExerciseCounter = 0
trainingCounter = 0

hour = 3600 * 1000
day = hour * 24
week = day * 7

names = ["super", "hardcore", "intensive", "fulfilling", "hard", "regular", "exhaustive", ]
muscleGroup = ["Chest",
               "Back",
               "Shoulders",
               "Arms",
               "Legs"]
adjective = ["strenght", "endurance", "flexibility", "stamina"]

duration = [1, 1.5, 2, 2.5]
numOfSeries = [2, 3, 4, 5]
restAfter = [30, 60, 90]
reps = [6, 8, 10, 12, 15]
base_date = 1582725600000  # 26.02.2020 15:00


def getWorkoutName(muscleGroup: str) -> str:
    return f'{choice(names)} {muscleGroup} {choice(adjective)}'


def getMuscleGroup() -> list:
    return list({choice(muscleGroup), choice(muscleGroup)})


def getCoach() -> str:
    num = randrange(40) + 1
    return str(num if num <= 35 else -1)


def getExercisesNums() -> list:
    return list({str(randrange(5)), str(randrange(5)), str(randrange(5))})


def getFinishDate(timestamp):
    return timestamp + int(choice(duration) * hour)


def getClient() -> str:
    return str(randrange(30) + 1)


def getInperson() -> bool:
    return False if randrange(10) < 7 else True


def getRestAfter() -> int:
    return choice(restAfter)


def getReps() -> int:
    return choice(reps)


def getWeight():
    return (randrange(10) + 1) * 10


def generateSeries():
    global seriesCounter
    series = choice(numOfSeries)
    res = []
    for i in range(series):
        tmp = {
            "id": str(seriesCounter),
            "reps": getReps(),
            "rest": getRestAfter(),
            "weight": getWeight()
        }
        seriesCounter += 1
        res.append(tmp)
    return res


def reduceSeries(seriesToDo):
    for i in range(len(seriesToDo)):
        if randrange(10) > 7:
            seriesToDo[i]["reps"] -= choice([1, 2, 3, 4, 5])
    return seriesToDo


def ciapki(val):
    return f"'{val}'" if str(val) != '-1' else 'null'

def getPlannedExercises(id: str, exercises: list, datefrom: int):
    res = []
    for exerciseNum in exercises:
        seriesToDo = generateSeries()
        seriesDone = [] if datefrom > datetime.datetime.now().timestamp() else reduceSeries(seriesToDo)
        res.append({
            'exerciseId': str(exerciseNum),
            'trainingId': str(id),
            'plannedSeries': seriesToDo,
            'doneSeries': seriesDone,
            'restAfter': getRestAfter()
        })
    return res


starting_string = 'insert into training(id, name, "muscle_group", coach_id, client_id, time_from, time_to, "planned_exercises", inperson) values ('

from collections import defaultdict
clientTocoach = defaultdict(set)
coopid=0

def regularClient(i_max, j_max):
    global trainingCounter
    resultStrings = []

    coachId = getCoach()
    clientId = getClient()

    clientTocoach[clientId].add(coachId)
    inperson = getInperson()

    datefrom = base_date + (randrange(20) - 10) * day + (randrange(6) - 3) * hour

    for i in range(i_max):
        datefrom += 2 * day
        muscleGroup = getMuscleGroup()
        name = getWorkoutName(muscleGroup[0])
        for j in range(j_max):
            id = trainingCounter
            trainingCounter += 1
            tmp_datefrom = datefrom + j * week
            date_to = getFinishDate(tmp_datefrom)
            exercises = getExercisesNums()
            planned_Exercises = getPlannedExercises(id, exercises, tmp_datefrom)
            muscles_dumped = json.dumps(muscleGroup)
            exercises_dumped = json.dumps(planned_Exercises)
            resultStrings.append(
                f"{starting_string}'{id}', '{name}', '{muscles_dumped}', {ciapki(coachId)}, '{clientId}', '{tmp_datefrom}', '{date_to}', '{exercises_dumped}', '{inperson}');"
            )
    return resultStrings


glob_res = []
for i in range(5):
    glob_res+= regularClient(3,7)

for i in range(5):
    glob_res+= regularClient(2,4)

for i in range(5):
    glob_res+= regularClient(1,1)

coop_list= []
for key in clientTocoach:
    for val in clientTocoach[key]:
        if str(val) !='-1':
            coop_list.append(f"insert into client_to_coach(id, client_id, coach_id) values('{coopid}','{key}',{ciapki(val)});")
            coopid+=1

traings_to_save = "\n".join(glob_res)
coops_to_save = "\n".join(coop_list)

with open("insert_trainings.sql","w+") as f:
    f.write(traings_to_save)

with open("insert_client_to_coach.sql","w+") as f:
    f.write(coops_to_save)

print(traings_to_save)
print("\n\n\n\n\n")
print(coops_to_save)



