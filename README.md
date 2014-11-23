# owl2-reasoners-benchmark

This is ta console java application that allows the speed evaluating OWL 2 reasoners on a specified ontologies. This application was implemented in the evaluating owl 2 reasoning process for the `ORE 2014` paper.

[![status](https://img.shields.io/badge/status-completed-inactive?style=flat-square)](BADGES_GUIDE.md#status) [![version](https://img.shields.io/badge/version-1.0.0-informational?style=flat-square)](BADGES_GUIDE.md#version) [![stable](https://img.shields.io/badge/stable-yes-important?style=flat-square)](BADGES_GUIDE.md#stable)  [![oss lifecycle](https://img.shields.io/badge/oss_lifecycle-archived-important?style=flat-square)](BADGES_GUIDE.md#oss-lifecycle) [![maintenance](https://img.shields.io/badge/maintenance-no-informational?style=flat-square)](BADGES_GUIDE.md#maintenance) [![latest release date](https://img.shields.io/badge/latest_release_date-June_25,_2014-informational?style=flat-square)](BADGES_GUIDE.md#release-date) [![last commit](https://img.shields.io/badge/last_commit-June_25,_2014-informational?style=flat-square)](BADGES_GUIDE.md#commit-date)

[![license](https://img.shields.io/badge/license-MIT-informational?style=flat-square)](LICENSE) [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg?style=flat-square)](code_of_conduct.md)

---

## 📇 Table of Contents

- [About](#about)
- [Demo](#demo)
- [Features](#feature)
- [Getting Started](#getting-started)
- [Built With](#built-with)
- [Contributing](#contributing)
- [Code of Conduct](#code-of-conduct)
- [Versioning](#versioning)
- [Authors](#authors)
- [Licensing](#licensing)

##  📖 About

This is a console java application that allows the speed evaluating OWL 2 reasoners on a specified ontologies. This application was implemented in the evaluating owl 2 reasoning process for the `ORE 2014` paper.

### Motivation

During developing a model for representing the relational databases semantics in DL axioms and assetions, it was necessary to evaluate the reasoning speed of such a solution. This benchmark was developed and allowed to evaluate the reasoning speed of OWL 2 reasoners (such as TReasoner, Hermit, Fact++). The results of this evaluating were presented at `OWL Reasoner Evaluation Workshop 2014` in this article http://ceur-ws.org/Vol-1207/paper_8.pdf.

## 📸 Demo

These pictures demonstrate the benchmark results examples (time in ns).

- The results in `*.xls`:
![The results in xls](https://github.com/ololx/owl2-reasoners-benchmark/blob/assets/demo/owl2-reasoner-benchmark-demo-2.png?raw=true)

- The log output in `*.html`:
![The log in html](https://github.com/ololx/owl2-reasoners-benchmark/blob/assets/demo/owl2-reasoner-benchmark-demo-3.png?raw=true)

- The log in console/terminal:
![The log in console/terminal](https://github.com/ololx/owl2-reasoners-benchmark/blob/assets/demo/owl2-reasoner-benchmark-demo-1.png?raw=true)

## 🎚 Features

- Choose reasoners and run an estimate of the time spent on the selected resonators on a set of ontologies;

### To Do

- ~~For more information on an upcoming development, please read the todo list.~~ No plans.

### Changelog

- For more information on a releases, a features and a changes, please read the [changelog](CHANGELOG.md) notes.

## 🚦 Getting Started

These instructions allow to get a copy of this project and run it on a local machine.

### Installing

In order to install it is quite simple to clone or download this repository.

### Cloning

For the cloning this repository to a local machine, just use the follows link:

```http
https://github.com/ololx/owl2-reasoners-benchmark
```

### Using

To use it is necessary to:
1 - Build the project (via IDE tools or Ant).
2 - Configure the `OWL2ReasonersBenchmark`.
3 - Launch the `OWL2ReasonersBenchmark`.

### Configuring

It is possible to set test parameters (rezoners, folder with test data, folder for saving results, etc.) via the configuration file `cfg.ini`. The example of configurations is presented below:

```cfg
SEPARATOR = ;
RESULTS_DIR = E:
TEST_DIR = E:/q
REASONERS_LIST = HErmit;Treasoner
REASONING_LIST = CONSISTENT
TEST_ITERATION = 1
DELAY_BETWEEN_ITERATION = 17
```

### Launching

- Firstly, it is necessary to put ontologies in the directory defined as `TEST_DIR` in `cfg.ini`.
![The input dir with test data](https://github.com/ololx/owl2-reasoners-benchmark/blob/assets/demo/owl2-reasoner-benchmark-demo-input.png?raw=true)
- To execute this benchmark.

- After execution the benchmark results will be in the directory defined as `RESULTS_DIR` in `cfg.ini`. 
![The output dir with results](https://github.com/ololx/owl2-reasoners-benchmark/blob/assets/demo/owl2-reasoner-benchmark-demo-output.png?raw=true)

## 🛠 Built With

- **[OWL API 3.4.10](https://github.com/owlcs/owlapi)** - the Java API for creating, manipulating and serialising OWL Ontologies.
- **[TREasoner](http://ceur-ws.org/Vol-1015/paper_2.pdf)** - the OWL DL Reasoner implemented in Java.
- **[FACT++ 1.6.2](https://www.w3.org/2001/sw/wiki/Fact)** - the OWL DL Reasoner implemented in C++.
- **[JFact](https://github.com/owlcs/jfact)** - the Java port of the FaCT++ OWL DL reasoner;.
- **[HermiT](https://github.com/phillord/hermit-reasoner)** - the OWL DL Reasoner implemented in Java.
- **[Pellet](https://github.com/stardog-union/pellet)** - the OWL DL Reasoner implemented in Java.
- **[MORe](https://www.cs.ox.ac.uk/isg/tools/MORe/)** - the OWL DL Reasoner implemented in Java.
- **[ELK](https://github.com/liveontologies/elk-reasoner)** - the OWL DL Reasoner implemented in Java.
- **[APACHE POI](https://poi.apache.org)** - the Java library for reading and writing Microsoft Office binary and OOXML file formats.
- **[Log4j](https://logging.apache.org/log4j/2.x/)** - the logging facade that may, of course, be used with the Log4j implementation, but may also be used in front of other logging implementations such as Logback.

## 🎉 Contributing

If you want to contribute this project - you are welcome and have fun.
Please visit the [contributing](CONTRIBUTING.md) section for details on this code of conduct, and the process for submitting pull requests.

## 📝 Code of Conduct

In order to ensure that all is welcoming, please review and abide by the [code of conduct](CODE_OF_CONDUCT.md).

## 🗒 Versioning

For the versioning is used [Semantic Versioning](http://semver.org/). For the versions available, see the [changelog](CHANGELOG.md) or the tags on this repository.

## ©️ Authors

* **Alexander A. Kropotin** - *Initial work* - [ololx](https://github.com/ololx).

## 🔏 Licensing

This project is licensed under the MIT license - see the [lisence](LICENSE) document for details.
