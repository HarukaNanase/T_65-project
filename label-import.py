from github3 import login
import github3
import getpass
import json


p = getpass.getpass()
gh = login('lolstorm92', password=p)

sigmavirus24 = gh.user('lolstorm92')
# <User [sigmavirus24:Ian Cordasco]>

print(sigmavirus24.name)
# Ian Cordasco
print(sigmavirus24.login)
# sigmavirus24
print(sigmavirus24.followers)
repository = gh.repository("tecnico-softeng-distsys-2015", "T_65-project")
# issue = gh.issue("tecnico-softeng-distsys-2015", "T_65-project", 1)
# print issue.__dict__
# repository.create_label("poodle", "cc317c")
# repository.remove_label("bug")
for x in repository.labels():
    print x
data = {}
with open('../github-labels.json') as data_file:
    data = json.load(data_file)
for label in data:
    try:
        print label['name'], label['color']
        repository.create_label(label['name'], label['color'])
    except github3.exceptions.UnprocessableEntity:
        pass

# print data

# 4

# for f in gh.iter_followers():
#    print(str(f))

# kennethreitz = gh.user('kennethreitz')
# <User [kennethreitz:Kenneth Reitz]>

# print(kennethreitz.name)
# print(kennethreitz.login)
# print(kennethreitz.followers)

# followers = [str(f) for f in gh.iter_followers('kennethreitz')]
