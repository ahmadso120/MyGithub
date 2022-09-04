This app uses the [Github API](https://docs.github.com/en/rest) to load data github user on the main
screen. To use the API, you will need to obtain a free token. See the
[Creating a personal access token documentation](https://docs.github.com/en/enterprise-server@3.4/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) for instructions.

Once you have the token, add this line to the `gradle.properties` file, either in your user home
directory (usually `~/.gradle/gradle.properties` on Linux and Mac) or in the project's root folder:

```
token=<your Github token>
base_url=https://api.github.com/
```
