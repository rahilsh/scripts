import requests

print('Enter GitHub token:')
token = input()

headers = dict()

headers['Authorization'] = 'token ' + token
for repo in open("../data/repos_to_delete.csv"):
    csv_row = repo.split()
    repo_name = csv_row[0]
    print(repo_name)
    url = url = 'https://api.github.com/repos/' + repo_name
    response = requests.delete(url=url, headers=headers)
    print(response)
